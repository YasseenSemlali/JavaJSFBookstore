package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Ads;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.AdsJpaController;
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
 * A java bean to edit ads.
 *
 * @author Jean Robatto
 */
@Named
@RequestScoped
public class ManagerAds implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerAds.class);

    @Inject
    private AdsJpaController adsController;

    private String[] locations;
    private String[] urls;
    private Boolean[] enabled;

    @Size(min = 1, max = 200) private String newLocation;
    @Size(min = 1, max = 2048) private String newUrl;

    /**
     * Method to initialize variables
     */
    @PostConstruct
    private void init() {
        int size = adsController.getAdsCount();
        locations = new String[size];
        urls = new String[size];
        enabled = new Boolean[size];
        for (int i = 0; i < size; i++) {
            locations[i] = "";
            urls[i] = "";
            this.enabled[i] = Boolean.FALSE;
        }
    }

    /**
     * Method to alter the state of an ad.
     *
     * @param id
     * @param index
     * @throws java.io.IOException
     */
    public void editAd(Long id, int index) throws IOException {
        try {
            Ads ad = adsController.findAds(id);

            //Only edit fields which have been edited
            if (!locations[index].isEmpty()) {
                ad.setFileLocation(locations[index]);
            }
            if (!urls[index].isEmpty()) {
                ad.setUrl(urls[index]);
            }
            if (!ad.getEnabled().equals(enabled[index])) {
                ad.setEnabled(enabled[index]);
            }

            adsController.edit(ad);

            //Reset inputs
            locations[index] = "";
            urls[index] = "";
            enabled[index] = Boolean.FALSE;

        } catch (BackendException ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }

    /**
     * Method to create a new ad.
     *
     * @return redirection
     */
    public String createAd() {
        try {
            Ads ad = new Ads();
            ad.setFileLocation(newLocation);
            ad.setUrl(newUrl);
            ad.setTimestamp(new Date());
            ad.setEnabled(Boolean.TRUE);
            
            adsController.create(ad);

            return "/manager-forms/manager-ads";
        } catch (BackendException | RollbackFailureException ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    public String[] getLocations() {
        return locations;
    }

    public String[] getUrls() {
        return urls;
    }

    public Boolean[] getEnabled() {
        return enabled;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public void setEnabled(Boolean[] enabled) {
        this.enabled = enabled;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

}
