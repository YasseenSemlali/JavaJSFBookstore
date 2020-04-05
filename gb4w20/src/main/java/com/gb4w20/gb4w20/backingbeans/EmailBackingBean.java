package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.TaxesJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import static jodd.mail.CommonEmail.PRIORITY_HIGHEST;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Email</h1>
 * <p>
 * This will perform sending email to the users and usually used to send the
 * invoice to the user
 * </p>
 *
 * @author Jasmar Badion
 */
@Named
@RequestScoped
public class EmailBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(EmailBackingBean.class);

    @Inject
    private UserSessionBean userSession;
    @Inject
    private TransactionBackingBean transaction;
    @Inject
    private CartBookBackingBean cart;
    @Inject
    private TaxesJpaController tax;

    //We made our own Gmail account for this project
    private final String emailSender;
    private final String smtpPassword;
    private final String smtpServerName;
    
    //Bundle for i18n
    private ResourceBundle bundle;

    /**
     * Constructor that retrieves the email credentials from the web.xml
     */
    public EmailBackingBean() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        emailSender
                = ctx.getExternalContext().getInitParameter("emailSender");
        smtpPassword
                = ctx.getExternalContext().getInitParameter("smtpPassword");
        smtpServerName
                = ctx.getExternalContext().getInitParameter("smtpServerName");
    }
    
    /**
     * Mainly used to set the bundle.
     *
     * @author Jeffrey Boisvert
     */
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.bundle = context.getApplication().getResourceBundle(context, "msgs");
    }

    /**
     * Perform sending email
     */
    public void perform() {
        sendEmail();

        // Add a five second pause to allow the Gmail server to receive what has
        // been sent
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Threaded sleep failed", e);
        }
    }

    /**
     * Sending email and checks if emails are valid
     * 
     */
    public void sendEmail() {
        LOG.info("Our email " + emailSender);
        LOG.info("To who: " + userSession.getUser().getEmail());
        
        //dialog from PrimeFaces is used depending on the outcome of this method
        PrimeFaces emailDialog = PrimeFaces.current();
        
        if (checkEmail(emailSender) && checkEmail(userSession.getUser().getEmail())) {
            LOG.debug("VALID EMAILS");
            // Create am SMTP server object
            SmtpServer smtpServer = MailServer.create()
                    .ssl(true)
                    .host(smtpServerName)
                    .auth(emailSender, smtpPassword)
                    .buildSmtpMailServer();
            LOG.debug("SMTP SUCCESS");
            
            // Using the fluent style of coding create a plain text message
            String emailBody = createEmailBody();
            Email email = Email.create().from(emailSender)
                    .to(userSession.getUser().getEmail())
                    .subject(this.bundle.getString("invoice"))
                    .textMessage(this.bundle.getString("companyName") + ": " + LocalDateTime.now())
                    .htmlMessage("<html><META http-equiv=Content-Type "
                            + "content=\"text/html; charset=utf-8\">"
                            + "<body><h1>Bookify " + this.bundle.getString("invoice") + "</h1>"
                            + "<p>" + emailBody + "</p>"
                            + "</body></html>")
                    .priority(PRIORITY_HIGHEST);
            LOG.debug("Email created");
            
            // Like a file we open the session, send the message and close the
            // session
            try (SendMailSession session = smtpServer.createSession()) {
                session.open();
                session.sendMail(email);
                LOG.info("Email sent");
                emailDialog.executeScript("PF('successEmail').show()");
            }    
        } else {
            LOG.info("Unable to send email because either send or recieve addresses are invalid");
            emailDialog.executeScript("PF('failEmail').show()");
        }
    }

    /**
     * Check if email is valid
     *
     * @param address
     * @return
     */
    private boolean checkEmail(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }
    
    /**
     * Creates the email body that will be sent to email
     * @return 
     */
    private String createEmailBody(){
        String items = "<h2>" + this.bundle.getString("youritems") + ": </h2>";
        for (Books book : cart.getBooks()){
            items += "<p> " + book.getTitle() + " $" + (book.getListPrice().subtract(book.getSalePrice())) + " </p>";
        }
        String amount = "<h3>" + this.bundle.getString("total") + ": $" + transaction.calculateAmountWithTaxes(cart.calculateTotalAmount(), tax.findByProvince(userSession.getUser().getProvince())) + " </h3>";
        String info = "<p>" + this.bundle.getString("credcardnum") + ": " + transaction.hideCreditCardNum() + 
                "</p><p>" + this.bundle.getString("datepurchase") + ": " + transaction.showCurrentDate() + "</p>";
        return items + amount + info + "<h2>" + this.bundle.getString("thankinvoice") + "</h2>";
    }
}
