package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.entities.Publishers;
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
    protected Long selectIsbn;
    protected Long isbn;
    protected String title;
    protected Date dateOfPublication;
    protected int pages;
    protected String synopsis;
    protected String cover;
    protected BigDecimal wholesalePrice;
    protected BigDecimal listPrice;
    protected BigDecimal salePrice;
    protected Date timestamp = new Date();
    protected boolean active;
    protected Collection<Authors> bookAuthor;
    protected Collection<Genres> bookGenre;
    protected Collection<Publishers> bookPublisher;

    protected Long authorToAdd;
    protected Long genreToAdd;
    protected Long publisherToAdd;

    //Author
    protected Long authorId;
    protected String authorFirstName;
    protected String authorLastName;

    //Publisher
    protected Long publisherId;
    protected String publisherName;

    //Genre
    protected Long genreId;
    protected String genre;
    
    //Total sales
    protected BigDecimal totalSales;

    //Cosntructor
    public ManagerInventory() {
    }

    //LISTENERS
    /**
     * Whenever a book is changed, wither fills the form fields with the info
     * from the book, or, if the book is a new book, it empties the fields.
     *
     * @param e
     */
    public void bookChanged(AjaxBehaviorEvent e) {
        Long isbn = (Long) ((UIOutput) e.getSource()).getValue();
        if (isbn == -1 || isbn == null) {
            this.edit = false;
            this.isbn = null;
            this.title = null;
            this.dateOfPublication = null;
            this.pages = 0;
            this.synopsis = null;
            this.cover = null;
            this.wholesalePrice = null;
            this.listPrice = null;
            this.salePrice = null;
            this.timestamp = null;
            this.active = true;
            this.bookAuthor = new ArrayList<>();
            this.bookGenre = new ArrayList<>();
            this.bookPublisher = new ArrayList<>();
            this.totalSales = new BigDecimal(0);
        } else {
            this.edit = true;
            Books book = booksController.findBooks(isbn);
            this.isbn = book.getIsbn();
            this.title = book.getTitle();
            this.dateOfPublication = book.getDateOfPublication();
            this.pages = book.getPages();
            this.synopsis = book.getSynopsis();
            this.cover = book.getCover();
            this.wholesalePrice = book.getWholesalePrice();
            this.listPrice = book.getListPrice();
            this.salePrice = book.getSalePrice();
            this.timestamp = book.getTimestamp();
            this.active = book.getActive();
            this.bookAuthor = book.getAuthorsCollection();
            this.bookGenre = book.getGenresCollection();
            this.bookPublisher = book.getPublishersCollection();
            this.totalSales = bookorderController.getTotalSalesForBook(book);
        }
    }

    /**
     * Whenever an author is changed, wither fills the form fields with the info
     * from the author, or, if the author is a new author, it empties the
     * fields.
     *
     * @param e
     */
    public void authorChanged(AjaxBehaviorEvent e) {
        Long authorId = (Long) ((UIOutput) e.getSource()).getValue();
        if (authorId == -1) {
            this.edit = false;
            this.authorFirstName = null;
            this.authorLastName = null;
        } else {
            this.edit = true;
            Authors author = authorsController.findAuthors(authorId);
            this.authorFirstName = author.getFirstName();
            this.authorLastName = author.getLastName();
        }
    }

    /**
     * Whenever a publisher is changed, wither fills the form fields with the
     * info from the publisher, or, if the publisher is a new publisher, it
     * empties the fields.
     *
     * @param e
     */
    public void publisherChanged(AjaxBehaviorEvent e) {
        Long publisherId = (Long) ((UIOutput) e.getSource()).getValue();
        if (publisherId == -1) {
            this.edit = false;
            this.publisherName = null;
        } else {
            this.edit = true;
            Publishers publisher = publishersController.findPublishers(publisherId);
            this.publisherName = publisher.getName();
        }
    }

    /**
     * Whenever a genre is changed, wither fills the form fields with the info
     * from the genre, or, if the genre is a new genre, it empties the fields.
     *
     * @param e
     */
    public void genreChanged(AjaxBehaviorEvent e) {
        Long genreId = (Long) ((UIOutput) e.getSource()).getValue();
        if (genreId == -1) {
            this.edit = false;
            this.genre = null;
        } else {
            this.edit = true;
            Genres genre = genresController.findGenres(genreId);
            this.genre = genre.getGenre();
        }
    }

    /**
     * Adds the selected author to the collection of authors for the currently
     * selected book.
     *
     * @param e
     */
    public void addAuthorToCollection(AjaxBehaviorEvent e) {
        Long authorId = ((Long) ((UIOutput) e.getSource()).getValue());
        Authors author = authorsController.findAuthors(authorId);
        if (this.bookAuthor != null) {
            this.bookAuthor.add(author);
        } else {
            this.bookAuthor = new ArrayList<Authors>() {
                {
                    add(author);
                }
            };
        }
    }

    /**
     * Removed all authors from the collection of authors for the currently
     * selected book.
     */
    public void clearAuthors() {
        this.bookAuthor = new ArrayList<>();
    }
    
    /**
     * Adds the selected genre to the collection of genres for the currently
     * selected book.
     *
     * @param e
     */
    public void addGenreToCollection(AjaxBehaviorEvent e) {
        Long genreId = ((Long) ((UIOutput) e.getSource()).getValue());
        Genres genre = genresController.findGenres(genreId);
        if (this.bookGenre != null) {
            this.bookGenre.add(genre);
        } else {
            this.bookGenre = new ArrayList<Genres>() {
                {
                    add(genre);
                }
            };
        }
    }

    /**
     * Removed all genres from the collection of genres for the currently
     * selected book.
     */
    public void clearGenres() {
        this.bookGenre = new ArrayList<>();
    }
    
    /**
     * Adds the selected publisher to the collection of publishers for the currently
     * selected book.
     *
     * @param e
     */
    public void addPublisherToCollection(AjaxBehaviorEvent e) {
        Long publisherId = ((Long) ((UIOutput) e.getSource()).getValue());
        Publishers publisher = publishersController.findPublishers(publisherId);
        if (this.bookPublisher != null) {
            this.bookPublisher.add(publisher);
        } else {
            this.bookPublisher = new ArrayList<Publishers>() {
                {
                    add(publisher);
                }
            };
        }
    }

    /**
     * Removed all publishers from the collection of publishers for the currently
     * selected book.
     */
    public void clearPublishers() {
        this.bookPublisher = new ArrayList<>();
    }

    //FORM SUBMISSIONS
    /**
     * Submits the book form. Will either edit an existing book, or add a new
     * book, depending on the selected field.
     *
     * @return redirection
     */
    public String submitBook() {
        if (this.edit) {
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
     */
    public String submitAuthor() {
        if (this.edit) {
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
     */
    public String submitGenre() {
        if (this.edit) {
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
     */
    public String submitPublisher() {
        if (this.edit) {
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
            newBook.setAuthorsCollection(this.bookAuthor);
            newBook.setGenresCollection(this.bookGenre);
            newBook.setPublishersCollection(this.bookPublisher);

            booksController.create(newBook);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";

        }
    }

    /**
     * Method to edit a book
     *
     * @return redirection
     */
    private String editBook() {
        try {
            Books editBook = booksController.findBooks(this.isbn);
            editBook.setIsbn(isbn);
            editBook.setTitle(title);
            editBook.setDateOfPublication(dateOfPublication);
            editBook.setSynopsis(synopsis);
            editBook.setCover(cover);
            editBook.setPages(pages);
            editBook.setWholesalePrice(wholesalePrice);
            editBook.setListPrice(listPrice);
            editBook.setSalePrice(salePrice);
            editBook.setActive(active);
            editBook.setAuthorsCollection(this.bookAuthor);
            editBook.setGenresCollection(this.bookGenre);
            editBook.setPublishersCollection(this.bookPublisher);

            booksController.edit(editBook);

            return "success_inventory";
        } catch (Exception ex) {
            LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            LOG.info(ex.getMessage());
            return "failure_inventory?error=" + ex.getMessage();
        }
    }

    /**
     * Method to add an author
     *
     * @return redirection
     */
    private String addAuthor() {
        try {
            Authors newAuthor = new Authors();
            newAuthor.setFirstName(this.authorFirstName);
            newAuthor.setLastName(this.authorLastName);

            authorsController.create(newAuthor);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
        }
    }

    /**
     * Method to edit an author
     *
     * @return redirection
     */
    private String editAuthor() {
        try {
            Authors editAuthor = authorsController.findAuthors(this.authorId);
            editAuthor.setFirstName(this.authorFirstName);
            editAuthor.setLastName(this.authorLastName);

            authorsController.edit(editAuthor);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
        }
    }

    /**
     * Method to add a genre
     *
     * @return redirection
     */
    private String addGenre() {
        LOG.info("GENRE CALLED ADD");
        try {
            Genres newGenre = new Genres();
            newGenre.setGenre(this.genre);

            genresController.create(newGenre);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
        }
    }

    /**
     * Method to edit a genre
     *
     * @return redirection
     */
    private String editGenre() {
        LOG.info("GENRE CALLED EDIT");
        LOG.info(Long.toString(genreId));
        try {
            Genres editGenre = genresController.findGenres(this.genreId);
            editGenre.setGenre(this.genre);

            genresController.edit(editGenre);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
        }
    }

    /**
     * Method to add a publisher
     *
     * @return redirection
     */
    private String addPublisher() {
        try {
            Publishers newPublisher = new Publishers();
            newPublisher.setName(this.publisherName);

            publishersController.create(newPublisher);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
        }
    }

    /**
     * Method to edit a publisher
     *
     * @return redirection
     */
    private String editPublisher() {
        try {
            Publishers editPulisher = publishersController.findPublishers(this.publisherId);
            editPulisher.setName(this.publisherName);

            publishersController.edit(editPulisher);

            return "success_inventory";
        } catch (Exception ex) {
            return "failure_inventory";
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
