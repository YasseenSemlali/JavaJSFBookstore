package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.entities.Publishers;
import com.gb4w20.gb4w20.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import com.gb4w20.gb4w20.jpa.PublishersJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java bean to manage the manager inventory page. Used to edit and/or add
 * authors, books, publishers and genres.
 *
 * @author jeanrobatto
 */
@Named
@SessionScoped
public class ManagerInventory implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerInventory.class);

    @Inject
    private BooksJpaController booksController;

    @Inject
    private AuthorsJpaController authorsController;

    @Inject
    private PublishersJpaController publishersController;

    @Inject
    private GenresJpaController genresController;

    @Inject
    private BookorderJpaController bookorderController;

    //Private fields
    private boolean edit;

    //Book
    private Long selectIsbn;
    private Long isbn;
    private String title;
    private Date dateOfPublication;
    private int pages;
    private String synopsis;
    private String cover;
    private BigDecimal wholesalePrice;
    private BigDecimal listPrice;
    private BigDecimal salePrice;
    private Date timestamp = new Date();
    private boolean active;
    private Collection<Authors> bookAuthor;
    private Collection<Genres> bookGenre;
    private Collection<Publishers> bookPublisher;

    private Long authorToAdd;
    private Long genreToAdd;
    private Long publisherToAdd;

    //Author
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;

    //Publisher
    private Long publisherId;
    private String publisherName;

    //Genre
    private Long genreId;
    private String genre;

    //Total sales
    private BigDecimal totalSales;

    //LISTENERS
    /**
     * Whenever a book is changed, wither fills the form fields with the info
     * from the book, or, if the book is a new book, it empties the fields.
     *
     * @param e
     * @author Jean Robatto
     */
    public void bookChanged(AjaxBehaviorEvent e) {
        Long input_isbn = (Long) ((UIOutput) e.getSource()).getValue();
        if (input_isbn == -1 || input_isbn == null) {
            edit = false;
            isbn = null;
            title = null;
            dateOfPublication = null;
            pages = 0;
            synopsis = null;
            cover = null;
            wholesalePrice = null;
            listPrice = null;
            salePrice = null;
            timestamp = null;
            active = true;
            bookAuthor = new ArrayList<>();
            bookGenre = new ArrayList<>();
            bookPublisher = new ArrayList<>();
            totalSales = new BigDecimal(0);
        } else {
            edit = true;
            Books book = booksController.findBooks(input_isbn);
            isbn = book.getIsbn();
            title = book.getTitle();
            dateOfPublication = book.getDateOfPublication();
            pages = book.getPages();
            synopsis = book.getSynopsis();
            cover = book.getCover();
            wholesalePrice = book.getWholesalePrice();
            listPrice = book.getListPrice();
            salePrice = book.getSalePrice();
            timestamp = book.getTimestamp();
            active = book.getActive();
            bookAuthor = book.getAuthorsCollection();
            bookGenre = book.getGenresCollection();
            bookPublisher = book.getPublishersCollection();
            totalSales = bookorderController.getTotalSalesForBook(book);
        }
    }

    /**
     * Whenever an author is changed, wither fills the form fields with the info
     * from the author, or, if the author is a new author, it empties the
     * fields.
     *
     * @param e
     * @author Jean Robatto
     */
    public void authorChanged(AjaxBehaviorEvent e) {
        Long input_authorId = (Long) ((UIOutput) e.getSource()).getValue();
        if (input_authorId == -1) {
            edit = false;
            authorFirstName = null;
            authorLastName = null;
        } else {
            edit = true;
            Authors author = authorsController.findAuthors(input_authorId);
            authorFirstName = author.getFirstName();
            authorLastName = author.getLastName();
        }
    }

    /**
     * Whenever a publisher is changed, wither fills the form fields with the
     * info from the publisher, or, if the publisher is a new publisher, it
     * empties the fields.
     *
     * @param e
     * @author Jean Robatto
     */
    public void publisherChanged(AjaxBehaviorEvent e) {
        Long input_publisherId = (Long) ((UIOutput) e.getSource()).getValue();
        if (input_publisherId == -1) {
            edit = false;
            publisherName = null;
        } else {
            edit = true;
            Publishers publisher = publishersController.findPublishers(input_publisherId);
            publisherName = publisher.getName();
        }
    }

    /**
     * Whenever a genre is changed, wither fills the form fields with the info
     * from the genre, or, if the genre is a new genre, it empties the fields.
     *
     * @param e
     * @author Jean Robatto
     */
    public void genreChanged(AjaxBehaviorEvent e) {
        Long input_genreId = (Long) ((UIOutput) e.getSource()).getValue();
        if (input_genreId == -1) {
            edit = false;
            genre = null;
        } else {
            edit = true;
            Genres new_genre = genresController.findGenres(input_genreId);
            genre = new_genre.getGenre();
        }
    }

    /**
     * Adds the selected author to the collection of authors for the currently
     * selected book.
     *
     * @param e
     * @author Jean Robatto
     */
    public void addAuthorToCollection(AjaxBehaviorEvent e) {
        Long input_authorId = ((Long) ((UIOutput) e.getSource()).getValue());
        Authors author = authorsController.findAuthors(input_authorId);
        if (bookAuthor != null) {
            bookAuthor.add(author);
        } else {
            bookAuthor = new ArrayList<Authors>() {
                {
                    add(author);
                }
            };
        }
    }

    /**
     * Removes an author from the collection
     *
     * @param author
     * @author Jean Robatto
     */
    public void removeAuthorFromCollection(Authors author) {
        bookAuthor.remove(author);
    }

    /**
     * Removed all authors from the collection of authors for the currently
     * selected book.
     *
     * @author Jean Robatto
     */
    public void clearAuthors() {
        bookAuthor = new ArrayList<>();
    }

    /**
     * Adds the selected genre to the collection of genres for the currently
     * selected book.
     *
     * @param e
     * @author Jean Robatto
     */
    public void addGenreToCollection(AjaxBehaviorEvent e) {
        Long input_genreId = ((Long) ((UIOutput) e.getSource()).getValue());
        Genres genreObj = genresController.findGenres(input_genreId);
        if (bookGenre != null) {
            bookGenre.add(genreObj);
        } else {
            bookGenre = new ArrayList<Genres>() {
                {
                    add(genreObj);
                }
            };
        }
    }

    /**
     * Removes an genre from the collection
     *
     * @param genre
     * @author Jean Robatto
     */
    public void removeGenreFromCollection(Genres genre) {
        bookGenre.remove(genre);
    }

    /**
     * Removed all genres from the collection of genres for the currently
     * selected book.
     *
     * @author Jean Robatto
     */
    public void clearGenres() {
        bookGenre = new ArrayList<>();
    }

    /**
     * Adds the selected publisher to the collection of publishers for the
     * currently selected book.
     *
     * @param e
     * @author Jean Robatto
     */
    public void addPublisherToCollection(AjaxBehaviorEvent e) {
        Long input_publisherId = ((Long) ((UIOutput) e.getSource()).getValue());
        Publishers publisher = publishersController.findPublishers(input_publisherId);
        if (bookPublisher != null) {
            bookPublisher.add(publisher);
        } else {
            bookPublisher = new ArrayList<Publishers>() {
                {
                    add(publisher);
                }
            };
        }
    }

    /**
     * Removes a publisher from the collection
     *
     * @param publisher
     * @author Jean Robatto
     */
    public void removePublisherFromCollection(Publishers publisher) {
        bookPublisher.remove(publisher);
    }

    /**
     * Removed all publishers from the collection of publishers for the
     * currently selected book.
     *
     * @author Jean Robatto
     */
    public void clearPublishers() {
        bookPublisher = new ArrayList<>();
    }

    //FORM SUBMISSIONS
    /**
     * Submits the book form. Will either edit an existing book, or add a new
     * book, depending on the selected field.
     *
     * @return redirection
     * @author Jean Robatto
     */
    public String submitBook() {
        if (edit) {
            return editBook();
        } else {
            return addBook();
        }
    }

    /**
     * Submits the author form. Will either edit an existing author, or add a
     * new author, depending on the selected field.
     *
     * @return redirection
     * @author Jean Robatto
     */
    public String submitAuthor() {
        if (edit) {
            return editAuthor();
        } else {
            return addAuthor();
        }
    }

    /**
     * Submits the genre form. Will either edit an existing genre, or add a new
     * genre, depending on the selected field.
     *
     * @return redirection
     * @author Jean Robatto
     */
    public String submitGenre() {
        if (edit) {
            return editGenre();
        } else {
            return addGenre();
        }
    }

    /**
     * Submits the publisher form. Will either edit an existing publisher, or
     * add a new publisher, depending on the selected field.
     *
     * @return redirection
     * @author Jean Robatto
     */
    public String submitPublisher() {
        if (edit) {
            return editPublisher();
        } else {
            return addPublisher();
        }
    }

    //ACTIONS
    /**
     * Method to add a book
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String addBook() {
        try {
            Books newBook = new Books();

            newBook.setIsbn(isbn);
            newBook.setTitle(title);
            newBook.setDateOfPublication(dateOfPublication);
            newBook.setSynopsis(synopsis);
            newBook.setCover(cover);
            newBook.setPages(pages);
            newBook.setWholesalePrice(wholesalePrice);
            newBook.setListPrice(listPrice);
            newBook.setSalePrice(salePrice);
            newBook.setActive(active);
            newBook.setAuthorsCollection(bookAuthor);
            newBook.setGenresCollection(bookGenre);
            newBook.setPublishersCollection(bookPublisher);

            booksController.create(newBook);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure.xhtml";
        }
    }

    /**
     * Method to edit a book
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String editBook() {
        try {
            Books editBook = booksController.findBooks(isbn);
            editBook.setTitle(title);
            editBook.setDateOfPublication(dateOfPublication);
            editBook.setSynopsis(synopsis);
            editBook.setCover(cover);
            editBook.setPages(pages);
            editBook.setWholesalePrice(wholesalePrice);
            editBook.setListPrice(listPrice);
            editBook.setSalePrice(salePrice);
            editBook.setActive(active);
            editBook.setAuthorsCollection(bookAuthor);
            editBook.setGenresCollection(bookGenre);
            editBook.setPublishersCollection(bookPublisher);

            booksController.edit(editBook);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to add an author
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String addAuthor() {
        try {
            Authors newAuthor = new Authors();
            newAuthor.setFirstName(authorFirstName);
            newAuthor.setLastName(authorLastName);

            authorsController.create(newAuthor);

            return "/action-responses/action-success";
        } catch (BackendException ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to edit an author
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String editAuthor() {
        try {
            Authors editAuthor = authorsController.findAuthors(authorId);
            editAuthor.setFirstName(authorFirstName);
            editAuthor.setLastName(authorLastName);

            authorsController.edit(editAuthor);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to add a genre
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String addGenre() {
        try {
            Genres newGenre = new Genres();
            newGenre.setGenre(genre);

            genresController.create(newGenre);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to edit a genre
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String editGenre() {
        try {
            Genres editGenre = genresController.findGenres(genreId);
            editGenre.setGenre(genre);

            genresController.edit(editGenre);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to add a publisher
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String addPublisher() {
        try {
            Publishers newPublisher = new Publishers();
            newPublisher.setName(publisherName);

            publishersController.create(newPublisher);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to edit a publisher
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String editPublisher() {
        try {
            Publishers editPulisher = publishersController.findPublishers(publisherId);
            editPulisher.setName(publisherName);

            publishersController.edit(editPulisher);

            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    //Setters
    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSelectIsbn(Long selectIsbn) {
        this.selectIsbn = selectIsbn;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public void setAuthorToAdd(Long authorToAdd) {
        this.authorToAdd = authorToAdd;
    }

    public void setGenreToAdd(Long genreToAdd) {
        this.genreToAdd = genreToAdd;
    }

    public void setPublisherToAdd(Long publisherToAdd) {
        this.publisherToAdd = publisherToAdd;
    }

    public void setBookAuthor(Collection<Authors> bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookGenre(Collection<Genres> bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setBookPublisher(Collection<Publishers> bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    //Getters
    public Long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public int getPages() {
        return pages;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCover() {
        return cover;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public Long getSelectIsbn() {
        return selectIsbn;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isEdit() {
        return edit;
    }

    public Long getAuthorToAdd() {
        return authorToAdd;
    }

    public Long getGenreToAdd() {
        return genreToAdd;
    }

    public Long getPublisherToAdd() {
        return publisherToAdd;
    }

    public Collection<Authors> getBookAuthor() {
        return bookAuthor;
    }

    public Collection<Genres> getBookGenre() {
        return bookGenre;
    }

    public Collection<Publishers> getBookPublisher() {
        return bookPublisher;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

}
