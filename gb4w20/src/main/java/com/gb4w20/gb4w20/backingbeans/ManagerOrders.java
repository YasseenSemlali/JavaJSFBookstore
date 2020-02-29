package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Taxes;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.OrdersJpaController;
import com.gb4w20.gb4w20.jpa.TaxesJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to manage the manager orders page. Used to edit and/or add
 * orders.
 *
 * @author jeanrobatto
 */
@Named
@SessionScoped
public class ManagerOrders implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);

    @Inject
    private BooksJpaController booksController;

    @Inject
    private BookorderJpaController bookorderController;

    @Inject
    private OrdersJpaController orderController;

    @Inject
    private UsersJpaController usersController;

    @Inject
    private TaxesJpaController taxController;

    //Main Fields
    protected Orders order;

    //Price and tax
    protected BigDecimal pricePreTax = new BigDecimal(0);
    protected BigDecimal hstTax = new BigDecimal(0);
    protected BigDecimal gstTax = new BigDecimal(0);
    protected BigDecimal pstTax = new BigDecimal(0);
    protected BigDecimal totalTax = new BigDecimal(0);
    protected BigDecimal totalPrice = new BigDecimal(0);

    //Books
    protected Collection<Bookorder> bookOrders;
    protected Long bookToAdd;

    //Info
    protected String address;
    protected Long selectedUserId;

    //Actions
    /**
     * Edits the order with the newly submitted inputs
     *
     * @return redirection
     * @uthor Jean Robatto
     */
    public String editOrder() {
        try {
            //Bookorders
            for (Bookorder bookorder : this.bookOrders) {
                bookorderController.edit(bookorder);
            }

            this.order.setBillingAddress(this.address);
            this.order.setBookorderCollection(this.bookOrders);
            this.order.setUserId(usersController.findUsers(this.selectedUserId));
            this.order.setTimestamp(new Date());

            orderController.edit(this.order);

            return "/action-responses/action-success";
        } catch (Exception e) {
            return "/action-responses/action-failure";
        }
    }

    /**
     * Adds the selected book to the collection of bookorders for the order
     *
     * @param e
     * @author Jean Robatto
     * @throws com.gb4w20.gb4w20.exceptions.RollbackFailureException
     */
    public void addBookToCollection(AjaxBehaviorEvent e) throws RollbackFailureException {
        Long isbn = ((Long) ((UIOutput) e.getSource()).getValue());
        Books book = booksController.findBooks(isbn);

        Bookorder bookorder = new Bookorder();

        BigDecimal bookprice = (book.getSalePrice().compareTo(new BigDecimal(0)) == 0) ? book.getListPrice() : book.getSalePrice();

        bookorder.setAmountPaidPretax(bookprice);
        bookorder.setEnabled(Boolean.TRUE);
        bookorder.setIsbn(book);
        bookorder.setOrderId(this.order);

        Map<String, BigDecimal> taxes = calculateTax(bookprice, this.order.getUserId().getProvince());

        bookorder.setGstTax(taxes.get("GST"));
        bookorder.setPstTax(taxes.get("PST"));
        bookorder.setHstTax(taxes.get("HST"));

        bookorderController.create(bookorder);

        if (this.bookOrders != null) {
            this.bookOrders.add(bookorder);
        } else {
            this.bookOrders = new ArrayList<Bookorder>() {
                {
                    add(bookorder);
                }
            };
        }
    }

    /**
     * Sets all the fields for a new or existing order
     *
     * @param order
     * @return redirection
     * @author Jean Robatto
     */
    public String selectOrder(Orders order) {
        if (order == null) {
            this.order = new Orders();

            //Set fields
            this.selectedUserId = null;
            this.bookOrders = new ArrayList<>();

            //Set taxes and prices
            this.pricePreTax = new BigDecimal(0);
            this.hstTax = new BigDecimal(0);
            this.gstTax = new BigDecimal(0);
            this.pstTax = new BigDecimal(0);
            this.totalTax = new BigDecimal(0);
            this.totalPrice = new BigDecimal(0);

            //Info
            this.address = "Address";

            //Get ID
            this.order.setBillingAddress(this.address);
            this.order.setBookorderCollection(this.bookOrders);
            this.order.setUserId(usersController.findUsersEntities().get(0));
            this.order.setTimestamp(new Date());

            orderController.create(this.order);

        } else {
            this.order = order;
            this.selectedUserId = this.order.getUserId().getUserId();
            this.bookOrders = order.getBookorderCollection();

            //Set taxes and prices
            this.pricePreTax = bookorderController.getTotalSalesForOrderPreTax(order);
            this.hstTax = (bookorderController.getHSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getHSTForOrder(order).setScale(2, BigDecimal.ROUND_DOWN);
            this.gstTax = (bookorderController.getGSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getGSTForOrder(order).setScale(2, BigDecimal.ROUND_DOWN);
            this.pstTax = (bookorderController.getPSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getPSTForOrder(order).setScale(2, BigDecimal.ROUND_DOWN);
            this.totalTax = this.hstTax.add(this.pstTax).add(this.gstTax).setScale(2, BigDecimal.ROUND_DOWN);
            this.totalPrice = this.pricePreTax.add(this.totalTax).setScale(2, BigDecimal.ROUND_DOWN);

            //Info
            this.address = order.getBillingAddress();
        }

        return "manager-orders-edit";
    }

    //Helpers
    /**
     * Helper function to calculate the tax
     *
     * @param basePrice
     * @return a map of tax type (HST, PST, GST) and the tax.
     * @author Jean Robatto
     */
    private HashMap<String, BigDecimal> calculateTax(BigDecimal basePrice, String province) {

        HashMap<String, BigDecimal> result = new HashMap<>();

        Taxes taxObj = taxController.findByProvince(province);

        if (taxObj.getHSTpercentage() != null) {
            result.put("HST", basePrice.multiply(taxObj.getHSTpercentage()));
        }
        if (taxObj.getGSTpercentage() != null) {
            result.put("GST", basePrice.multiply(taxObj.getGSTpercentage()));
        }
        if (taxObj.getPSTpercentage() != null) {
            result.put("PST", basePrice.multiply(taxObj.getPSTpercentage()));
        }

        return result;
    }

    //Setters
    public void setBookOrders(Collection<Bookorder> bookOrders) {
        this.bookOrders = bookOrders;
    }

    public void setBookToAdd(Long bookToAdd) {
        this.bookToAdd = bookToAdd;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setPricePreTax(BigDecimal pricePreTax) {
        this.pricePreTax = pricePreTax;
    }

    public void setHstTax(BigDecimal hstTax) {
        this.hstTax = hstTax;
    }

    public void setGstTax(BigDecimal gstTax) {
        this.gstTax = gstTax;
    }

    public void setPstTax(BigDecimal pstTax) {
        this.pstTax = pstTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    //Getters
    public Collection<Bookorder> getBookOrders() {
        return bookOrders;
    }

    public Long getBookToAdd() {
        return bookToAdd;
    }

    public Orders getOrder() {
        return order;
    }

    public BigDecimal getPricePreTax() {
        return pricePreTax;
    }

    public BigDecimal getHstTax() {
        return hstTax;
    }

    public BigDecimal getGstTax() {
        return gstTax;
    }

    public BigDecimal getPstTax() {
        return pstTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    /*
    
    switch (province) {
            case "AB": 
            case "NU":
            case "NT":
            case "YT":
                result.put("HST", null);
                result.put("PST", null);
                result.put("GST", basePrice.multiply(new BigDecimal(0.05)));
                break;
                
            case "BC":
                result.put("HST", null);
                result.put("PST", basePrice.multiply(new BigDecimal(0.07)));
                result.put("GST", basePrice.multiply(new BigDecimal(0.05)));
                break;
                
            case "MB":
                result.put("HST", null);
                result.put("PST", basePrice.multiply(new BigDecimal(0.08)));
                result.put("GST", basePrice.multiply(new BigDecimal(0.05)));
                break;
                
            case "NB":
            case "NL":
            case "NS":
            case "PE":
                result.put("HST", basePrice.multiply(new BigDecimal(0.15)));
                result.put("PST", null);
                result.put("GST", null);
                break;
                
            case "ON":
                result.put("HST", basePrice.multiply(new BigDecimal(0.13)));
                result.put("PST", null);
                result.put("GST", null);
                break;
                
            case "QC":
                result.put("HST", null);
                result.put("PST", basePrice.multiply(new BigDecimal(0.09975)));
                result.put("GST", basePrice.multiply(new BigDecimal(0.05)));
                break;
                
            case "SK":
                result.put("HST", null);
                result.put("PST", basePrice.multiply(new BigDecimal(0.06)));
                result.put("GST", basePrice.multiply(new BigDecimal(0.05)));
                break;
        }
     */
}
