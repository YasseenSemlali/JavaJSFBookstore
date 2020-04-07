
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 * Stores the most recently viewed genre
 *
 * @author Yasseen
 */
@Named
@RequestScoped
public class LastViewedGenreBean {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LastViewedGenreBean.class);

    public static String RECENT_GENRE_COOKIE_NAME = "recentGenre";

    @Inject
    BooksJpaController booksJpaController;
    
    public void setMostRecentGenre(List<Genres> genres) {
        LOG.info("Setting mose recent genre");
    
        FacesContext context = FacesContext.getCurrentInstance();

        if(genres == null || genres.size() == 0) {
            LOG.info("Recent genre was empty");
            return;
        }
        
        LOG.info("Setting mose recent genre to " + genres.get(0).getGenreId().toString());
        Cookie cookie = new Cookie(RECENT_GENRE_COOKIE_NAME, genres.get(0).getGenreId().toString());
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);

        ((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(cookie);
    }

    private Long getMostRecentGenreId() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        Cookie cookie = (Cookie) requestCookieMap.get(RECENT_GENRE_COOKIE_NAME);
        
        if(cookie == null) {
            return null;
        }
        
        return Long.parseLong(cookie.getValue());
    }
    
    public List<Books> getBookRecommendations() {
        Long id = this.getMostRecentGenreId();
        
        if(id == null) {
            return null;
        }
        return booksJpaController.getAllBooksForGenre(id);
    }
}
