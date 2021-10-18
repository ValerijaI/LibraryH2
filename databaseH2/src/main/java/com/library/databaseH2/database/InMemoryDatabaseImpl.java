package com.library.databaseH2.database;

import com.library.databaseH2.domain.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class InMemoryDatabaseImpl implements Database{

    List<Book> bookList = new ArrayList<>();
    Long id = 1L;

    @Override
    public void addBook(Book book) {
        book.setId(id);
       bookList.add(book);
       id++;
    }

    @Override
    public boolean deleteBookById(Long id) {
        return bookList.removeIf(book -> book.getId() == id);
    }

    @Override
    public List<Book> getBooks() {
        return bookList;
    }

    @Override
    public List<Book> searchBookByAuthor(String author) {
        return bookList.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBookByTitleAndAuthor(String author, String title) {
        return bookList.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author) && book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }
}
