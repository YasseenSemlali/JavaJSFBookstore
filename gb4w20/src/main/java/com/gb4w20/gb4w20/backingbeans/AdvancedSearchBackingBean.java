
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.querybeans.NameAndNumberBean;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backing bean for an advanced search
 * 
 * @author Yasseen Semlali
 */
@Named
@RequestScoped
public class AdvancedSearchBackingBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(AdvancedSearchBackingBean.class);
    
    private String title;
    private String author;
    private String publisher;
    private Boolean allTrue;
    private Boolean useExact;

    public Boolean getAllTrue() {
        return allTrue;
    }

    public void setAllTrue(Boolean allTrue) {
        this.allTrue = allTrue;
    }

    public Boolean getUseExact() {
        return useExact;
    }

    public void setUseExact(Boolean useExact) {
        this.useExact = useExact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    
}
