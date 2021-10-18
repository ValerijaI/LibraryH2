package com.library.databaseH2.entity;

import com.library.databaseH2.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public Book convertEntityToDomain(BookEntity book) {
        return new Book(book.getId(), book.getTitle(), book.getAuthor());
    }

    public BookEntity convertDomainToBookEntity(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setTitle(book.getTitle());
        return bookEntity;
    }
}
