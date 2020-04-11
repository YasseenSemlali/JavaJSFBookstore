package com.gb4w20.backingbeans;

import com.gb4w20.entities.Ads;
import com.gb4w20.jpa.AdsJpaController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * A very simple backing bean to store
 * two random ads per session.
 * 
 * @author jeanrobatto
 */
@Named
@SessionScoped
public class ClientAdsBackingBean implements Serializable {
    
    private Ads ad1;
    private Ads ad2;
    
    @Inject
    private AdsJpaController adsJpaController;
    
    
    @PostConstruct
    private void init() {
        ad1 = adsJpaController.getRandomActiveAd();
        ad2 = adsJpaController.getRandomActiveAd();
    }

    public Ads getAd1() {
        return ad1;
    }

    public Ads getAd2() {
        return ad2;
    }

    public void setAd1(Ads ad1) {
        this.ad1 = ad1;
    }

    public void setAd2(Ads ad2) {
        this.ad2 = ad2;
    }
    
    
    
}
