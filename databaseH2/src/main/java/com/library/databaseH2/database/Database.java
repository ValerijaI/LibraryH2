package com.library.databaseH2.database;

import com.library.databaseH2.domain.Book;

import java.util.List;

public interface Database {

    void addBook(Book book);

    boolean deleteBookById(Long id);

    List<Book>getBooks();

    List<Book> searchBookByAuthor(String author);

    List<Book> searchBookByTitle(String title);

    List<Book> searchBookByTitleAndAuthor(String author, String title);
}
