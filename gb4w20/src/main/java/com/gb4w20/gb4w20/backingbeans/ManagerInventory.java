package com.gb4w20.gb4w20.backingbeans;

import com.gb4w20.gb4w20.entities.Authors;
import com.gb4w20.gb4w20.entities.BookFiles;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.Genres;
import com.gb4w20.gb4w20.entities.Publishers;
import com.gb4w20.gb4w20.jpa.exceptions.BackendException;
import com.gb4w20.gb4w20.jpa.exceptions.RollbackFailureException;
import com.gb4w20.gb4w20.jpa.AuthorsJpaController;
import com.gb4w20.gb4w20.jpa.BookFilesJpaController;
import com.gb4w20.gb4w20.jpa.BookorderJpaController;
import com.gb4w20.gb4w20.jpa.BooksJpaController;
import com.gb4w20.gb4w20.jpa.FileFormatsJpaController;
import com.gb4w20.gb4w20.jpa.GenresJpaController;
import com.gb4w20.gb4w20.jpa.PublishersJpaController;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
    private UserSessionBean userSessionBean;
    
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
    
    @Inject
    private BookFilesJpaController bookfilesController;
    
    @Inject
    private FileFormatsJpaController fileformatsController;

    //Private fields
    private boolean edit;
    
    private Date today = new Date();

    //Book
    private Long selectIsbn;
    private Long isbn;
    @Size(min = 1, max = 100)
    private String title;
    @Past
    private Date dateOfPublication;
    private int pages;
    @Size(min = 1, max = 1000)
    private String synopsis;
    private UploadedFile uploadedCover;
    private String cover = ""; 
    private BigDecimal wholesalePrice;
    private BigDecimal listPrice;
    private BigDecimal salePrice;
    private Date timestamp = new Date();
    private boolean active;
    private Collection<Authors> bookAuthor = new ArrayList<>();
    private Collection<Genres> bookGenre = new ArrayList<>();
    private Collection<Publishers> bookPublisher = new ArrayList<>();
    
    private Long authorToAdd;
    private Long genreToAdd;
    private Long publisherToAdd;
    
    private Collection<String> newBookfiles = new ArrayList<>();

    //Author
    private Long authorId;
    @Size(min = 1, max = 50)
    private String authorFirstName;
    @Size(min = 1, max = 50)
    private String authorLastName;

    //Publisher
    private Long publisherId;
    @Size(min = 1, max = 50)
    private String publisherName;

    //Genre
    private Long genreId;
    @Size(min = 1, max = 50)
    private String genre;

    //Total sales
    private BigDecimal totalSales = new BigDecimal(0);

    //Variables
    private final long EPUB_ID = 1L;
    private final long PDF_ID = 2L;
    private final long MOBI_ID = 3L;
    
    private boolean isNew = true;

    //INIT
    @PostConstruct
    private void init() {
        //Redirect if not manager
        try {
            if (!userSessionBean.isLoggedInManager()) {
                LOG.info("Must be logged in as manager to access this page.");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/gb4w20/index.xhtml");
            }
        } catch (IOException ex) {
            LOG.debug(ex.toString());
        }
    }

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
            LOG.debug("Selected new book");
            edit = false;
            isbn = null;
            title = null;
            dateOfPublication = null;
            pages = 0;
            synopsis = null;
            cover = "";
            wholesalePrice = null;
            listPrice = null;
            salePrice = null;
            timestamp = null;
            active = true;
            bookAuthor = new ArrayList<>();
            bookGenre = new ArrayList<>();
            bookPublisher = new ArrayList<>();
            totalSales = new BigDecimal(0);
            isNew = true;
        } else {
            LOG.debug("Selected book with ISBN: " + Long.toString(input_isbn));
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
            totalSales = bookorderController.getTotalSalesForBook(isbn);
            isNew = false;
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
            LOG.debug("Selected new author");
            edit = false;
            authorFirstName = null;
            authorLastName = null;
        } else {
            LOG.debug("Selected author with id: " + Long.toString(input_authorId));
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
            LOG.debug("Selected new publisher");
            edit = false;
            publisherName = null;
        } else {
            LOG.debug("Selected publisher with id: " + Long.toString(input_publisherId));
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
            LOG.debug("Selected new genre");
            edit = false;
            genre = null;
        } else {
            LOG.debug("Selected genre with id: " + Long.toString(input_genreId));
            edit = true;
            Genres new_genre = genresController.findGenres(input_genreId);
            genre = new_genre.getGenre();
        }
    }

    /**
     * Upload the cover image file to the server.
     *
     * @author Jean Robatto
     * @param event
     */
    public void handleImageUpload(FileUploadEvent event) {
        String basePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/../../src/main/webapp/resources/img/covers");
        UploadedFile newFile = event.getFile();
        cover = newFile.getFileName();
        saveUploadedFile(newFile, basePath);
    }

    /**
     * Upload a book file to the server.
     *
     * @author Jean Robatto
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        String basePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/../../src/main/webapp/resources/bookfiles");
        UploadedFile newFile = event.getFile();
        saveUploadedFile(newFile, basePath);

        //Save bookfile name to persist it later
        newBookfiles.add(newFile.getFileName());
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

    /**
     * Adds the selected author to the collection of authors for the currently
     * selected book.
     *
     * @param e
     * @author Jean Robatto
     */
    public void addAuthorToCollection(AjaxBehaviorEvent e) {
        LOG.debug("Adding author to book author collection");
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
        LOG.debug("Removing author from collection");
        bookAuthor.remove(author);
    }

    /**
     * Removed all authors from the collection of authors for the currently
     * selected book.
     *
     * @author Jean Robatto
     */
    public void clearAuthors() {
        LOG.debug("Cleared all authors from collection");
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
        LOG.debug("Adding genre to book genre collection");
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
        LOG.debug("Removing genre from collection");
        bookGenre.remove(genre);
    }

    /**
     * Removed all genres from the collection of genres for the currently
     * selected book.
     *
     * @author Jean Robatto
     */
    public void clearGenres() {
        LOG.debug("Cleared all genres from collection");
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
        LOG.debug("Adding publisher to book publisher collection");
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
        LOG.debug("Removing publisher from collection");
        bookPublisher.remove(publisher);
    }

    /**
     * Removed all publishers from the collection of publishers for the
     * currently selected book.
     *
     * @author Jean Robatto
     */
    public void clearPublishers() {
        LOG.debug("Cleared all publshers from collection");
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
        LOG.debug("Adding new book to database");
        try {
            Books newBook = new Books();
            
            newBook.setIsbn(isbn);
            newBook.setTitle(title);
            newBook.setDateOfPublication(dateOfPublication);
            newBook.setTimestamp(timestamp);
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

            //Book files
            for (String fileName : newBookfiles) {
                createBookFile(fileName, newBook);
            }
            
            newBookfiles = new ArrayList<>();
            
            return "/action-responses/action-success";
        } catch (RollbackFailureException ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Editing book with ISBN: " + Long.toString(isbn));
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

            //Book files
            for (String fileName : newBookfiles) {
                createBookFile(fileName, editBook);
            }
            
            newBookfiles = new ArrayList<>();
            
            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.debug(ex.toString());
            return "/action-responses/action-failure";
        }
    }

    /**
     * Method to create a book file. NOTE: Not persisted to database
     *
     * @param fileName
     * @author Jean Robatto
     */
    private void createBookFile(String fileName, Books book) {
        LOG.debug("Creating bookfile entry for " + fileName);
        BookFiles file = new BookFiles();
        
        file.setIsbn(book);
        file.setFileLocation("./" + fileName);
        
        String format = fileName.split("\\.")[1];
        
        Long format_id;
        
        switch (format) {
            case "pdf":
            case "PDF":
                format_id = PDF_ID;
                break;
            case "mobi":
            case "MOBI":
                format_id = MOBI_ID;
                break;
            case "epub":
            case "EPUB":
                format_id = EPUB_ID;
                break;
            default:
                format_id = -1L;
            
        }
        
        file.setFileFormatId(fileformatsController.findFileFormats(format_id));
        
        bookfilesController.create(file);
        
    }
    
    public void removeBookFile(Long id) {
        try {
            bookfilesController.destroy(id);
        } catch (NonexistentEntityException ex) {
            LOG.debug(ex.toString());
        }
    }

    /**
     * Method to add an author
     *
     * @return redirection
     * @author Jean Robatto
     */
    private String addAuthor() {
        LOG.debug("Adding new author to database");
        try {
            Authors newAuthor = new Authors();
            newAuthor.setFirstName(authorFirstName);
            newAuthor.setLastName(authorLastName);
            
            authorsController.create(newAuthor);
            
            return "/action-responses/action-success";
        } catch (BackendException ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Editing author with id: " + Long.toString(authorId));
        try {
            Authors editAuthor = authorsController.findAuthors(authorId);
            editAuthor.setFirstName(authorFirstName);
            editAuthor.setLastName(authorLastName);
            
            authorsController.edit(editAuthor);
            
            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Adding new genre to database");
        try {
            Genres newGenre = new Genres();
            newGenre.setGenre(genre);
            
            genresController.create(newGenre);
            
            return "/action-responses/action-success";
        } catch (BackendException ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Editing genre with id: " + Long.toString(genreId));
        try {
            Genres editGenre = genresController.findGenres(genreId);
            editGenre.setGenre(genre);
            
            genresController.edit(editGenre);
            
            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Adding new publisher to database");
        try {
            Publishers newPublisher = new Publishers();
            newPublisher.setName(publisherName);
            
            publishersController.create(newPublisher);
            
            return "/action-responses/action-success";
        } catch (BackendException ex) {
            LOG.debug(ex.toString());
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
        LOG.debug("Editing publisher with id: " + Long.toString(publisherId));
        try {
            Publishers editPulisher = publishersController.findPublishers(publisherId);
            editPulisher.setName(publisherName);
            
            publishersController.edit(editPulisher);
            
            return "/action-responses/action-success";
        } catch (Exception ex) {
            LOG.debug(ex.toString());
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
    
    public void setToday(Date today) {
        this.today = today;
    }
    
    public void setUploadedCover(UploadedFile uploadedCover) {
        this.uploadedCover = uploadedCover;
    }
    
    public void setNewBookfiles(Collection<String> newBookfiles) {
        this.newBookfiles = newBookfiles;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
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
    
    public Date getToday() {
        return today;
    }
    
    public UploadedFile getUploadedCover() {
        return uploadedCover;
    }
    
    public Collection<String> getNewBookfiles() {
        return newBookfiles;
    }

    public boolean isIsNew() {
        return isNew;
    }
    
}
