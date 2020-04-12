/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jeanrobatto
 */
@Entity
@Table(name = "file_formats")
@NamedQueries({
    @NamedQuery(name = "FileFormats.findAll", query = "SELECT f FROM FileFormats f"),
    @NamedQuery(name = "FileFormats.findByFileFormatId", query = "SELECT f FROM FileFormats f WHERE f.fileFormatId = :fileFormatId"),
    @NamedQuery(name = "FileFormats.findByFormat", query = "SELECT f FROM FileFormats f WHERE f.format = :format")})
public class FileFormats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "file_format_id")
    private Long fileFormatId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "format")
    private String format;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fileFormatId")
    private Collection<BookFiles> bookFilesCollection;

    public FileFormats() {
    }

    public FileFormats(Long fileFormatId) {
        this.fileFormatId = fileFormatId;
    }

    public FileFormats(Long fileFormatId, String format) {
        this.fileFormatId = fileFormatId;
        this.format = format;
    }

    public Long getFileFormatId() {
        return fileFormatId;
    }

    public void setFileFormatId(Long fileFormatId) {
        this.fileFormatId = fileFormatId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Collection<BookFiles> getBookFilesCollection() {
        return bookFilesCollection;
    }

    public void setBookFilesCollection(Collection<BookFiles> bookFilesCollection) {
        this.bookFilesCollection = bookFilesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileFormatId != null ? fileFormatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileFormats)) {
            return false;
        }
        FileFormats other = (FileFormats) object;
        if ((this.fileFormatId == null && other.fileFormatId != null) || (this.fileFormatId != null && !this.fileFormatId.equals(other.fileFormatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gb4w20.entities.FileFormats[ fileFormatId=" + fileFormatId + " ]";
    }
    
}
