package com.library.databaseH2.responses;

import com.library.databaseH2.domain.Book;

import java.util.List;

public class GetAllBooksResponse extends CoreResponse {

    private List<Book> bookList;

    public GetAllBooksResponse(List<Book> bookList) {
        this.bookList = bookList;
    }

    public GetAllBooksResponse(List<Book> bookList, List<CoreError> errorList) {
        super(errorList);
        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
