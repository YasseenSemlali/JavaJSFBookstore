package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Bookorder;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Orders;
import com.gb4w20.gb4w20.entities.Taxes;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.OrdersJpaController;
import com.gb4w20.gb4w20.jpa.TaxesJpaController;
import com.gb4w20.gb4w20.jpa.UsersJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
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

    private final static Logger LOG = LoggerFactory.getLogger(ManagerOrders.class);

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
    private Orders order;
    private Boolean newOrder;
    private Boolean orderEnabled;

    //Price and tax
    private BigDecimal pricePreTax = new BigDecimal(0);
    private BigDecimal hstTax = new BigDecimal(0);
    private BigDecimal gstTax = new BigDecimal(0);
    private BigDecimal pstTax = new BigDecimal(0);
    private BigDecimal totalTax = new BigDecimal(0);
    private BigDecimal totalPrice = new BigDecimal(0);

    //Books
    private Collection<Bookorder> bookOrders;
    private Long bookToAdd;

    //Info
    private String address;
    private Long selectedUserId;

    //Actions
    /**
     * Edits the order with the newly submitted inputs
     *
     * @return redirection
     * @uthor Jean Robatto
     */
    public String editOrder() {
        LOG.debug("Editing order with id: " + Long.toString(order.getOrderId()));
        try {
            //Bookorders
            for (Bookorder bookorder : bookOrders) {
                bookorderController.edit(bookorder);
            }

            order.setBillingAddress(address);
            order.setBookorderCollection(bookOrders);
            order.setUserId(usersController.findUsers(selectedUserId));
            order.setTimestamp(new Date());
            order.setEnabled(orderEnabled);

            orderController.edit(order);

            //This redirect is needed to fix a rendering issue.
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/manager-forms/manager-orders.xhtml");

            return "/manager-forms/manager-orders";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Cancel a new order. Delete the order entry.
     *
     * @return redirection
     * @uthor Jean Robatto
     */
    public String cancelOrder() {
        LOG.debug("Cancelling order with id: " + Long.toString(order.getOrderId()));
        try {
            orderController.destroy(order.getOrderId());
            //This redirect is needed to fix a rendering issue.
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/manager-forms/manager-orders.xhtml");
            return "/manager-forms/manager-orders";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Adds the selected book to the collection of bookorders for the order
     *
     * @param e
     * @author Jean Robatto
     * @throws com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException
     * @throws com.gb4w20.gb4w20.jpa.exceptions.BackendException
     */
    public void addBookToCollection(AjaxBehaviorEvent e) throws RollbackFailureException, BackendException {
        Long isbn = ((Long) ((UIOutput) e.getSource()).getValue());
        
        LOG.debug("Adding book to order collection with ISBN: " + Long.toString(isbn));
        
        Books book = booksController.findBooks(isbn);

        Bookorder bookorder = new Bookorder();

        BigDecimal bookprice = book.getListPrice().subtract(book.getSalePrice());

        bookorder.setAmountPaidPretax(bookprice);
        bookorder.setEnabled(Boolean.TRUE);
        bookorder.setIsbn(book);
        bookorder.setOrderId(order);

        Map<String, BigDecimal> taxes = calculateTax(bookprice, order.getUserId().getProvince());

        bookorder.setGstTax(taxes.get("GST"));
        bookorder.setPstTax(taxes.get("PST"));
        bookorder.setHstTax(taxes.get("HST"));

        bookorderController.create(bookorder);

        if (bookOrders != null) {
            bookOrders.add(bookorder);
        } else {
            bookOrders = new ArrayList<Bookorder>() {
                {
                    add(bookorder);
                }
            };
        }
    }

    /**
     * Sets all the fields for a new or existing order
     *
     * @param selected_order
     * @return redirection
     * @author Jean Robatto
     * @throws com.gb4w20.gb4w20.jpa.exceptions.BackendException
     */
    public String selectOrder(Orders selected_order) throws BackendException {
        if (selected_order == null) {
            LOG.debug("Selected a new order");
            order = new Orders();

            //Set fields
            selectedUserId = null;
            bookOrders = new ArrayList<>();

            //Set taxes and prices
            pricePreTax = new BigDecimal(0);
            hstTax = new BigDecimal(0);
            gstTax = new BigDecimal(0);
            pstTax = new BigDecimal(0);
            totalTax = new BigDecimal(0);
            totalPrice = new BigDecimal(0);

            //Info
            address = "Address";

            //Get ID
            order.setBillingAddress(address);
            order.setBookorderCollection(bookOrders);
            order.setUserId(usersController.findUsersEntities().get(0));
            order.setTimestamp(new Date());
            order.setEnabled(Boolean.TRUE);

            orderController.create(order);

            newOrder = true;

            orderEnabled = order.getEnabled();

        } else {
            LOG.debug("Selected order with id: " + Long.toString(selected_order.getOrderId()));
            order = selected_order;
            selectedUserId = order.getUserId().getUserId();
            bookOrders = order.getBookorderCollection();

            //Set taxes and prices
            pricePreTax = bookorderController.getTotalSalesForOrderPreTax(order);
            hstTax = (bookorderController.getHSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getHSTForOrder(order).setScale(2, RoundingMode.HALF_EVEN);
            gstTax = (bookorderController.getGSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getGSTForOrder(order).setScale(2, RoundingMode.HALF_EVEN);
            pstTax = (bookorderController.getPSTForOrder(order) == null) ? new BigDecimal(0) : bookorderController.getPSTForOrder(order).setScale(2, RoundingMode.HALF_EVEN);
            totalTax = hstTax.add(pstTax).add(gstTax).setScale(2, RoundingMode.HALF_EVEN);
            totalPrice = pricePreTax.add(totalTax).setScale(2, RoundingMode.HALF_EVEN);

            //Info
            address = order.getBillingAddress();

            newOrder = false;

            orderEnabled = order.getEnabled();
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

    public void setNewOrder(Boolean newOrder) {
        this.newOrder = newOrder;
    }

    public void setOrderEnabled(Boolean orderEnabled) {
        this.orderEnabled = orderEnabled;
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

    public Boolean getNewOrder() {
        return newOrder;
    }

    public Boolean getOrderEnabled() {
        return orderEnabled;
    }

}
