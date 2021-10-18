package com.library.databaseH2.responses;

import com.library.databaseH2.domain.Book;

import java.util.List;

public class SearchBookResponse extends CoreResponse{

    private List<Book> bookList;

    public SearchBookResponse(List<Book> bookList) {
        this.bookList = bookList;
    }

    public SearchBookResponse(List<Book> bookList, List<CoreError> errorList) {
        super(errorList);
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
