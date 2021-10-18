package com.library.databaseH2.responses;

import com.library.databaseH2.domain.Book;

import java.util.List;

public class AddBookResponse extends CoreResponse {

    private Book book;

    public AddBookResponse(List<CoreError> errorList) {
        super(errorList);
    }

    public AddBookResponse(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
