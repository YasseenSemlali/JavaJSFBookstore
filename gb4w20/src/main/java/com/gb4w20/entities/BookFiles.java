/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author jeanrobatto
 */
@Entity
@Table(name = "book_files")
@NamedQueries({
    @NamedQuery(name = "BookFiles.findAll", query = "SELECT b FROM BookFiles b"),
    @NamedQuery(name = "BookFiles.findByBookFileId", query = "SELECT b FROM BookFiles b WHERE b.bookFileId = :bookFileId"),
    @NamedQuery(name = "BookFiles.findByFileLocation", query = "SELECT b FROM BookFiles b WHERE b.fileLocation = :fileLocation")})
public class BookFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "book_file_id")
    private Long bookFileId;
    @Size(max = 200)
    @Column(name = "file_location")
    private String fileLocation;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Books isbn;
    @JoinColumn(name = "file_format_id", referencedColumnName = "file_format_id")
    @ManyToOne(optional = false)
    private FileFormats fileFormatId;

    public BookFiles() {
    }

    public BookFiles(Long bookFileId) {
        this.bookFileId = bookFileId;
    }

    public Long getBookFileId() {
        return bookFileId;
    }

    public void setBookFileId(Long bookFileId) {
        this.bookFileId = bookFileId;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Books getIsbn() {
        return isbn;
    }

    public void setIsbn(Books isbn) {
        this.isbn = isbn;
    }

    public FileFormats getFileFormatId() {
        return fileFormatId;
    }

    public void setFileFormatId(FileFormats fileFormatId) {
        this.fileFormatId = fileFormatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookFileId != null ? bookFileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookFiles)) {
            return false;
        }
        BookFiles other = (BookFiles) object;
        if ((this.bookFileId == null && other.bookFileId != null) || (this.bookFileId != null && !this.bookFileId.equals(other.bookFileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.entities.BookFiles[ bookFileId=" + bookFileId + " ]";
    }
    
}
