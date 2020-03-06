package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.RssFeeds;
import com.gb4w20.gb4w20.jpa.RssFeedsJpaController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    
    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);
    
    @Inject
    private RssFeedsJpaController newsController;
    
    private String[] urls;
    private Boolean[] enabled;
    
    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = newsController.getRssFeedsCount();
        this.urls = new String[size];
        this.enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            this.urls[i] = "";
            this.enabled[i] = Boolean.FALSE;
        }
    }
    
    
    /**
     * Method to alter the state of a news feed
     * 
     * @param id
     * @param index
     */
    public void editFeed(Long id, int index) {
        try {
            RssFeeds feed = newsController.findRssFeeds(id);
            feed.setEnabled(this.enabled[index]);
            feed.setUrl(this.urls[index]);
            newsController.edit(feed);
            this.enabled[index] = Boolean.FALSE;
            this.urls[index] = "";
        } catch (Exception ex) {
            LOG.info(ex.toString());
        }
    }

    public String[] getUrls() {
        return urls;
    }

    public Boolean[] getEnabled() {
        return enabled;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public void setEnabled(Boolean[] enabled) {
        this.enabled = enabled;
    }
    
}
