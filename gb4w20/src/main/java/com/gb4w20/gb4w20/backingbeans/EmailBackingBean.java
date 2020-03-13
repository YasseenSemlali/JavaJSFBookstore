/*
 * Backing beans
 */
package com.gb4w20.gb4w20.backingbeans;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.activation.DataSource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.mail.Flags;
import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapServer;
import jodd.mail.MailServer;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Email</h1>
 * <p>
 * This will perform sending email to the users and usually used
 * to send the invoice to the user
 * </p>
 *
 * @author Jasmar Badion
 */
@Named
@RequestScoped
public class EmailBackingBean implements Serializable{
    private final static Logger LOG = LoggerFactory.getLogger(EmailBackingBean.class);
    
    private final String smtpServerName = "smtp.gmail.com";
    //private final String imapServerName = "imap.gmail.com";
    
    private final String emailSend = "jembadion@gmail.com";
    private final String emailSendPwd = "youcantseeme35";
    
    private String emailReceive;
    //private String emailReceive = "jasmar.badion@gmail.com";
    
    /**
     * Getter for emailReceive
     * @return 
     */
    public String getEmailReceive(){
        return this.emailReceive;
    }
    
    /**
     * Setter for emailReceive
     * @param emailReceive 
     */
    public void setEmailReceive(String emailReceive){
        this.emailReceive = emailReceive;
    }
    
    /**
     * Perform sending email
     */
    public void perform(){
        sendEmail();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOG.error("Threaded sleep failed", e);
            System.exit(1);
        }
    }
    
    /**
     * Sending email
     */
    public void sendEmail(){
        if (checkEmail(emailSend) && checkEmail(emailReceive)) {
            LOG.debug("VALID EMAILS");
            // Create am SMTP server object
            SmtpServer smtpServer = MailServer.create()
                    .ssl(true)
                    .host(smtpServerName)
                    .auth(emailSend, emailSendPwd)
                    //.debugMode(true)
                    .buildSmtpMailServer();
            LOG.debug("SMTP SUCCESS");
            // Using the fluent style of coding create a plain text message
            Email email = Email.create().from(emailSend)
                    .to(emailReceive)
                    .subject("Send Invoice")
                    .textMessage("Hello from plain text email: " + LocalDateTime.now())
                    .htmlMessage("<html><META http-equiv=Content-Type "
                            + "content=\"text/html; charset=utf-8\">"
                            + "<body><h1>Bookify Invoice</h1>"
                            + "<h2>Your Invoice</h2></body></html>");
            LOG.debug("Email created");
            // Like a file we open the session, send the message and close the
            // session
            try (SendMailSession session = smtpServer.createSession()) {
                session.open();
                session.sendMail(email);
                LOG.info("Email sent");
            }
        }  else {
            LOG.info("Unable to send email because either send or recieve addresses are invalid");
        }
    }
    
    /**
     * Check if email exists
     * @param address
     * @return 
     */
    private boolean checkEmail(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }
}
