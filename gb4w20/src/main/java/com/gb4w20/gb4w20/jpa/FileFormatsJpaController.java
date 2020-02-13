
package com.gb4w20.gb4w20.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.gb4w20.gb4w20.entities.BookFiles;
import com.gb4w20.gb4w20.entities.FileFormats;
import com.gb4w20.gb4w20.jpa.exceptions.IllegalOrphanException;
import com.gb4w20.gb4w20.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to interact with the file formats table in the database. 
 * @author Jeffrey Boisvert
 */
public class FileFormatsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(FileFormatsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "BookPU")
    private EntityManager em;

    public void create(FileFormats fileFormats) {
        if (fileFormats.getBookFilesCollection() == null) {
            fileFormats.setBookFilesCollection(new ArrayList<BookFiles>());
        }
        try {
            em.getTransaction().begin();
            Collection<BookFiles> attachedBookFilesCollection = new ArrayList<BookFiles>();
            for (BookFiles bookFilesCollectionBookFilesToAttach : fileFormats.getBookFilesCollection()) {
                bookFilesCollectionBookFilesToAttach = em.getReference(bookFilesCollectionBookFilesToAttach.getClass(), bookFilesCollectionBookFilesToAttach.getBookFileId());
                attachedBookFilesCollection.add(bookFilesCollectionBookFilesToAttach);
            }
            fileFormats.setBookFilesCollection(attachedBookFilesCollection);
            em.persist(fileFormats);
            for (BookFiles bookFilesCollectionBookFiles : fileFormats.getBookFilesCollection()) {
                FileFormats oldFileFormatIdOfBookFilesCollectionBookFiles = bookFilesCollectionBookFiles.getFileFormatId();
                bookFilesCollectionBookFiles.setFileFormatId(fileFormats);
                bookFilesCollectionBookFiles = em.merge(bookFilesCollectionBookFiles);
                if (oldFileFormatIdOfBookFilesCollectionBookFiles != null) {
                    oldFileFormatIdOfBookFilesCollectionBookFiles.getBookFilesCollection().remove(bookFilesCollectionBookFiles);
                    oldFileFormatIdOfBookFilesCollectionBookFiles = em.merge(oldFileFormatIdOfBookFilesCollectionBookFiles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FileFormats fileFormats) throws IllegalOrphanException, NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            FileFormats persistentFileFormats = em.find(FileFormats.class, fileFormats.getFileFormatId());
            Collection<BookFiles> bookFilesCollectionOld = persistentFileFormats.getBookFilesCollection();
            Collection<BookFiles> bookFilesCollectionNew = fileFormats.getBookFilesCollection();
            List<String> illegalOrphanMessages = null;
            for (BookFiles bookFilesCollectionOldBookFiles : bookFilesCollectionOld) {
                if (!bookFilesCollectionNew.contains(bookFilesCollectionOldBookFiles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookFiles " + bookFilesCollectionOldBookFiles + " since its fileFormatId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<BookFiles> attachedBookFilesCollectionNew = new ArrayList<BookFiles>();
            for (BookFiles bookFilesCollectionNewBookFilesToAttach : bookFilesCollectionNew) {
                bookFilesCollectionNewBookFilesToAttach = em.getReference(bookFilesCollectionNewBookFilesToAttach.getClass(), bookFilesCollectionNewBookFilesToAttach.getBookFileId());
                attachedBookFilesCollectionNew.add(bookFilesCollectionNewBookFilesToAttach);
            }
            bookFilesCollectionNew = attachedBookFilesCollectionNew;
            fileFormats.setBookFilesCollection(bookFilesCollectionNew);
            fileFormats = em.merge(fileFormats);
            for (BookFiles bookFilesCollectionNewBookFiles : bookFilesCollectionNew) {
                if (!bookFilesCollectionOld.contains(bookFilesCollectionNewBookFiles)) {
                    FileFormats oldFileFormatIdOfBookFilesCollectionNewBookFiles = bookFilesCollectionNewBookFiles.getFileFormatId();
                    bookFilesCollectionNewBookFiles.setFileFormatId(fileFormats);
                    bookFilesCollectionNewBookFiles = em.merge(bookFilesCollectionNewBookFiles);
                    if (oldFileFormatIdOfBookFilesCollectionNewBookFiles != null && !oldFileFormatIdOfBookFilesCollectionNewBookFiles.equals(fileFormats)) {
                        oldFileFormatIdOfBookFilesCollectionNewBookFiles.getBookFilesCollection().remove(bookFilesCollectionNewBookFiles);
                        oldFileFormatIdOfBookFilesCollectionNewBookFiles = em.merge(oldFileFormatIdOfBookFilesCollectionNewBookFiles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fileFormats.getFileFormatId();
                if (findFileFormats(id) == null) {
                    throw new NonexistentEntityException("The fileFormats with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        try {
            em.getTransaction().begin();
            FileFormats fileFormats;
            try {
                fileFormats = em.getReference(FileFormats.class, id);
                fileFormats.getFileFormatId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fileFormats with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BookFiles> bookFilesCollectionOrphanCheck = fileFormats.getBookFilesCollection();
            for (BookFiles bookFilesCollectionOrphanCheckBookFiles : bookFilesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FileFormats (" + fileFormats + ") cannot be destroyed since the BookFiles " + bookFilesCollectionOrphanCheckBookFiles + " in its bookFilesCollection field has a non-nullable fileFormatId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fileFormats);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FileFormats> findFileFormatsEntities() {
        return findFileFormatsEntities(true, -1, -1);
    }

    public List<FileFormats> findFileFormatsEntities(int maxResults, int firstResult) {
        return findFileFormatsEntities(false, maxResults, firstResult);
    }

    private List<FileFormats> findFileFormatsEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FileFormats.class));
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

    public FileFormats findFileFormats(Long id) {
        try {
            return em.find(FileFormats.class, id);
        } finally {
            em.close();
        }
    }

    public int getFileFormatsCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FileFormats> rt = cq.from(FileFormats.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
