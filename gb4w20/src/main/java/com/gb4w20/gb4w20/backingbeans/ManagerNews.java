package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.RssFeedsJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to edit news.
 *
 * @author Jean Robatto
 */
@Named
@RequestScoped
public class ManagerNews implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerNews.class);

    @Inject
    private RssFeedsJpaController newsController;

    private String[] urls;
    private Boolean[] enabled;
    
    @Size(min = 1, max = 2048)private String newUrl;

    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        LOG.debug("Initializing Manager News variables");
        int size = newsController.getRssFeedsCount();
        urls = new String[size];
        enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            urls[i] = "";
            enabled[i] = Boolean.FALSE;
        }
    }

    /**
     * Method to alter the state of a news feed
     *
     * @param id
     * @param index
     * @throws java.io.IOException
     */
    public void editFeed(Long id, int index) throws IOException {
        LOG.debug("Editing feed with id: " + Long.toString(id));
        try {
            RssFeeds feed = newsController.findRssFeeds(id);

            //Only edit fields which have been edited
            if (!feed.getEnabled().equals(enabled[index])) {
                feed.setEnabled(enabled[index]);
            }
            if (!urls[index].isEmpty()) {
                feed.setUrl(urls[index]);
            }

            newsController.edit(feed);

            //Reset inputs
            enabled[index] = Boolean.FALSE;
            urls[index] = "";

        } catch (Exception ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }
    
    /**
     * Method to create a new feed.
     * @return redirection
     */
    public String createFeed() {
        LOG.debug("Creating a new feed");
        try {
            RssFeeds feed = new RssFeeds();
            feed.setUrl(newUrl);
            feed.setTimestamp(new Date());
            feed.setEnabled(Boolean.TRUE);
            
            newsController.create(feed);

            return "/manager-forms/manager-news";
        } catch (BackendException ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    public String[] getUrls() {
        return urls;
    }

    public Boolean[] getEnabled() {
        return enabled;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public void setEnabled(Boolean[] enabled) {
        this.enabled = enabled;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    
}
