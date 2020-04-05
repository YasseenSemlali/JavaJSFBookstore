package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Ads;
import com.gb4w20.gb4w20.jpa.AdsJpaController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to edit ads.
 *
 * NOTE: For some exceptions, the full path was required. I assume Netbeans had
 * a little issue.
 *
 * @author Jean Robatto
 */
@Named
@SessionScoped
public class ManagerAds implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerAds.class);

    @Inject
    private AdsJpaController adsController;

    private String image;

    private String[] locations;
    private String[] urls;
    private Boolean[] enabled;

    @Size(min = 1, max = 2048)
    private String newUrl;

    /**
     * Method to initialize variables
     *
     * @author Jean Robatto
     */
    @PostConstruct
    private void init() {
        LOG.debug("Initializing manager ads backing bean");
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
     * @author Jean Robatto
     */
    public void editAd(Long id, int index) throws IOException {
        LOG.debug("Editing ad #" + Long.toString(id));
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

        } catch (com.gb4w20.gb4w20.exceptions.BackendException ex) {
            LOG.info(ex.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/action-responses/action-failure.xhtml");
        }
    }

    /**
     * Method to create a new ad.
     *
     * @return redirection
     * @author Jean Robatto
     */
    public String createAd() {
        LOG.debug("Creating new ad");
        try {
            Ads ad = new Ads();
            ad.setFileLocation(image);
            ad.setUrl(newUrl);
            ad.setTimestamp(new Date());
            ad.setEnabled(Boolean.TRUE);

            adsController.create(ad);
            
            init();

            return "/manager-secured/manager-forms/manager-ads";
        } catch (com.gb4w20.gb4w20.exceptions.BackendException | com.gb4w20.gb4w20.exceptions.RollbackFailureException ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Upload the ad image file to the server.
     *
     * @author Jean Robatto
     * @param event
     */
    public void handleImageUpload(FileUploadEvent event) {
        String basePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/../../src/main/webapp/resources/img/ads");
        UploadedFile newFile = event.getFile();
        image = newFile.getFileName();
        saveUploadedFile(newFile, basePath);
    }

    /**
     * Method to save a file into the project
     *
     * @author Jean Robatto
     */
    private void saveUploadedFile(UploadedFile newFile, String basePath) {
        try (InputStream input = newFile.getInputstream()) {
            File file = new File(basePath + "/" + newFile.getFileName());
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
            LOG.debug("Successfully uploaded file " + file.getName());
        } catch (Exception ex) {
            LOG.debug(ex.toString());
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

    public String getNewUrl() {
        return newUrl;
    }

    public String getImage() {
        return image;
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

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
