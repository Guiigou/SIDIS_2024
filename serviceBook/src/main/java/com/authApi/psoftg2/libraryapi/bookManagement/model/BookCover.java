package com.authApi.psoftg2.libraryapi.bookManagement.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class BookCover {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Book book;

    @Lob
    private byte[] image;

    private String contentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "BookCover{" +
                "id=" + id +
                ", book=" + book +
                ", image=" + Arrays.toString(image) +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}


