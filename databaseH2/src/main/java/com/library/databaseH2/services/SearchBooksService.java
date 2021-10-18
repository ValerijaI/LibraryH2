package com.library.databaseH2.services;

import com.library.databaseH2.domain.Book;
import com.library.databaseH2.entity.BookConverter;
import com.library.databaseH2.repository.LibraryRepository;
import com.library.databaseH2.requests.Ordering;
import com.library.databaseH2.requests.Paging;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.SearchBookResponse;
import com.library.databaseH2.validators.SearchBookRequestValidator;
import com.library.databaseH2.validators.SearchBookResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchBooksService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private SearchBookRequestValidator searchBookRequestValidator;

    @Autowired
    private SearchBookResponseValidator searchBookResponseValidator;

    @Autowired
    private BookConverter bookConverter;

//    @Value("${search.ordering.enabled}")
    private boolean orderingEnabled = true;

//    @Value("${search.paging.enabled}")
    private boolean pagingEnabled = true;

    public SearchBookResponse execute(SearchBookRequest searchBookRequest) {
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        if (!errorList.isEmpty()) {
            return new SearchBookResponse(new ArrayList<>(), errorList);
        }

        List<Book> bookList = getBookList(searchBookRequest);

        errorList = searchBookResponseValidator.validate(bookList);

        if (!errorList.isEmpty()) {
            return new SearchBookResponse(new ArrayList<>(), errorList);
        }

        bookList = sortedBookList(bookList, searchBookRequest.getOrdering());
        bookList = getListWithPaging(bookList, searchBookRequest.getPaging());

        return new SearchBookResponse(bookList);
    }

    private List<Book> getBookList(SearchBookRequest searchBookRequest) {
        if (authorIsFilled(searchBookRequest) && titleIsFilled(searchBookRequest)) {
            return findBooksByAuthorAndTitle(searchBookRequest.getAuthor(), searchBookRequest.getTitle());
        }
        if (authorIsFilled(searchBookRequest)) {
            return findByAuthor(searchBookRequest.getAuthor());
        }
        return findByTitle(searchBookRequest.getTitle());
    }

    private boolean authorIsFilled(SearchBookRequest searchBookRequest) {
        return searchBookRequest.getAuthor() != null && !searchBookRequest.getAuthor().isEmpty();
    }

    private boolean titleIsFilled(SearchBookRequest searchBookRequest) {
        return searchBookRequest.getTitle() != null && !searchBookRequest.getTitle().isEmpty();
    }

    private List<Book> sortedBookList(List<Book> bookList, Ordering ordering) {
        if (orderingEnabled) {
            Comparator<Book> comparator = ordering.getOrderBy().equalsIgnoreCase("Author")
                    ? Comparator.comparing(Book::getAuthor)
                    : Comparator.comparing((Book::getTitle));
            if (ordering.getDirection().equalsIgnoreCase("DESC")) {
                comparator = comparator.reversed();
            }
            return bookList.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }
        return bookList;
    }

    private List<Book> getListWithPaging(List<Book> bookList, Paging paging) {
        if (pagingEnabled) {
            int skipBooks = (paging.getPageNumber() - 1) * paging.getPageSize();
            return bookList.stream()
                    .skip(skipBooks)
                    .limit(paging.getPageSize())
                    .collect(Collectors.toList());
        }
        return bookList;
    }

    //private List<Book> paging(List<Book> books, Paging paging) {
    //		if (pagingEnabled && (paging != null)) {
    //			int skip = (paging.getPageNumber() - 1) * paging.getPageSize();
    //			return books.stream()
    //					.skip(skip)
    //					.limit(paging.getPageSize())
    //					.collect(Collectors.toList());
    //		} else {
    //			return books;
    //		}
    //	}

    private List<Book> getBookList() {
        return libraryRepository.findAll()
                .stream()
                .map(bookEntity -> bookConverter.convertEntityToDomain(bookEntity))
                .collect(Collectors.toList());
    }

    private List<Book> findBooksByAuthorAndTitle(String author, String title) {
        return getBookList().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author) && book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    private List<Book> findByAuthor(String author) {
        return getBookList().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    private List<Book> findByTitle(String title) {
        return getBookList().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }
}
