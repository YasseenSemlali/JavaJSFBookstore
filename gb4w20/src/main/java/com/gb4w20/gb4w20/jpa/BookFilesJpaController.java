
package com.gb4w20.gb4w20.jpa;

import static com.gb4w20.gb4w20.entities.Authors_.authorId;
import com.gb4w20.gb4w20.entities.BookFiles;
import com.gb4w20.gb4w20.entities.BookFiles_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.Books;
import com.gb4w20.gb4w20.entities.FileFormats;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the book files table. 
 * 
 * @author Jeffrey Boisvert, Jean Robatto
 */
@Named
@SessionScoped
public class BookFilesJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(BookFilesJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(BookFiles bookFiles) {
        try {
            utx.begin();
            Books isbn = bookFiles.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getIsbn());
                bookFiles.setIsbn(isbn);
            }
            FileFormats fileFormatId = bookFiles.getFileFormatId();
            if (fileFormatId != null) {
                fileFormatId = em.getReference(fileFormatId.getClass(), fileFormatId.getFileFormatId());
                bookFiles.setFileFormatId(fileFormatId);
            }
            em.persist(bookFiles);
            if (isbn != null) {
                isbn.getBookFilesCollection().add(bookFiles);
                isbn = em.merge(isbn);
            }
            if (fileFormatId != null) {
                fileFormatId.getBookFilesCollection().add(bookFiles);
                fileFormatId = em.merge(fileFormatId);
            }
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with create in bookfiles controller method.", ex);
        }
    }

    public void edit(BookFiles bookFiles) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            BookFiles persistentBookFiles = em.find(BookFiles.class, bookFiles.getBookFileId());
            Books isbnOld = persistentBookFiles.getIsbn();
            Books isbnNew = bookFiles.getIsbn();
            FileFormats fileFormatIdOld = persistentBookFiles.getFileFormatId();
            FileFormats fileFormatIdNew = bookFiles.getFileFormatId();
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getIsbn());
                bookFiles.setIsbn(isbnNew);
            }
            if (fileFormatIdNew != null) {
                fileFormatIdNew = em.getReference(fileFormatIdNew.getClass(), fileFormatIdNew.getFileFormatId());
                bookFiles.setFileFormatId(fileFormatIdNew);
            }
            bookFiles = em.merge(bookFiles);
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getBookFilesCollection().remove(bookFiles);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getBookFilesCollection().add(bookFiles);
                isbnNew = em.merge(isbnNew);
            }
            if (fileFormatIdOld != null && !fileFormatIdOld.equals(fileFormatIdNew)) {
                fileFormatIdOld.getBookFilesCollection().remove(bookFiles);
                fileFormatIdOld = em.merge(fileFormatIdOld);
            }
            if (fileFormatIdNew != null && !fileFormatIdNew.equals(fileFormatIdOld)) {
                fileFormatIdNew.getBookFilesCollection().add(bookFiles);
                fileFormatIdNew = em.merge(fileFormatIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = bookFiles.getBookFileId();
                if (findBookFiles(id) == null) {
                    throw new NonexistentEntityException("The bookFiles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        try {
            utx.begin();
            BookFiles bookFiles;
            try {
                bookFiles = em.getReference(BookFiles.class, id);
                bookFiles.getBookFileId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookFiles with id " + id + " no longer exists.", enfe);
            }
            Books isbn = bookFiles.getIsbn();
            if (isbn != null) {
                isbn.getBookFilesCollection().remove(bookFiles);
                isbn = em.merge(isbn);
            }
            FileFormats fileFormatId = bookFiles.getFileFormatId();
            if (fileFormatId != null) {
                fileFormatId.getBookFilesCollection().remove(bookFiles);
                fileFormatId = em.merge(fileFormatId);
            }
            em.remove(bookFiles);
            utx.commit();
       } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | SystemException | SecurityException | IllegalStateException ex) {
            LOG.error("Error with delete in book files controller method.", ex);
        }
    }

    public List<BookFiles> findBookFilesEntities() {
        return findBookFilesEntities(true, -1, -1);
    }

    public List<BookFiles> findBookFilesEntities(int maxResults, int firstResult) {
        return findBookFilesEntities(false, maxResults, firstResult);
    }

    private List<BookFiles> findBookFilesEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookFiles.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BookFiles findBookFiles(Long id) {
        try {
            return em.find(BookFiles.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * Used to get a all bookfiles for a book
     *
     * @param isbn
     * @return bookfiles for a specific book
     * @author Jean Robatto
     */
    public List<BookFiles> getBookFilesForBook(Books isbn) {
        if (isbn==null) {
            return null;
        }
        
        LOG.info("Getting all bookfiles for " + Long.toString(isbn.getIsbn()));

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookFiles> cq = cb.createQuery(BookFiles.class);

        Root<BookFiles> bookfile = cq.from(BookFiles.class);
        
        cq.select(bookfile).where(cb.equal(bookfile.get(BookFiles_.isbn), isbn));
        
        Query query = em.createQuery(cq);

        return query.getResultList();
    }

}
