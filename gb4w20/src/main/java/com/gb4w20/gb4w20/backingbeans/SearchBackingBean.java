
package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import java.io.IOException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing bean for search functionality
 *
 * @author Yasseen
 */
@Named
@RequestScoped
public class SearchBackingBean {

    @Inject
    BooksJpaController booksJpaController;

    /**Searches given the parameters
     * @author Yasseen Semlali
     */
    public List<Books> search(Long isbn, String title, String author, String publisher, Boolean allTrue) throws IOException {
        List<Books> results = booksJpaController.searchBooks(isbn, title, author, publisher, allTrue);

        if (results.size() != 1) {
            return results;
        } else {
            FacesContext.getCurrentInstance().getExternalContext().dispatch("bookpage.xhtml?isbn=" + results.get(0).getIsbn());
        }

        return null;
    }
}
