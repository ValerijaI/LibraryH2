package com.library.databaseH2.services;

import com.library.databaseH2.database.Database;
import com.library.databaseH2.domain.Book;
import com.library.databaseH2.requests.Ordering;
import com.library.databaseH2.requests.Paging;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.SearchBookResponse;
import com.library.databaseH2.validators.SearchBookRequestValidator;
import com.library.databaseH2.validators.SearchBookResponseValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SearchBooksServiceTest {

    private boolean orderingEnabled = true;
    private boolean pagingEnabled = true;

    @Mock
    private Database database;

    @Mock
    private SearchBookRequestValidator searchBookRequestValidator;

    @Mock
    private SearchBookResponseValidator searchBookResponseValidator;

    @InjectMocks
    private SearchBooksService searchBooksService;

    @Test
    public void testBookIsFound() {
        Ordering ordering = new Ordering("Title", "ASC");
        Paging paging = new Paging(1, 1);
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", ordering, paging);

        given(searchBookRequestValidator.validate(searchBookRequest)).willReturn(new ArrayList<>());
        List<Book> bookList = Collections.singletonList(new Book(1L, "Title", "Author"));
        given(database.searchBookByTitle("Title")).willReturn(bookList);
        given(searchBookResponseValidator.validate(bookList)).willReturn(new ArrayList<>());

        SearchBookResponse searchBookResponse = searchBooksService.execute(searchBookRequest);

        assertFalse(searchBookResponse.hasErrors());
        assertThat(searchBookResponse.getBookList().size()).isEqualTo(1);
    }

    @Test
    public void testRequestValidationError() {
        Ordering ordering = new Ordering("Title", "ASC");
        Paging paging = new Paging(1, 1);
        SearchBookRequest searchBookRequest = new SearchBookRequest(null,"",  ordering, paging);

        List<CoreError> errorList = Collections.singletonList(new CoreError("title/author", "Search parameters can't be empty"));
        given(searchBookRequestValidator.validate(searchBookRequest)).willReturn(errorList);

        SearchBookResponse searchBookResponse = searchBooksService.execute(searchBookRequest);

        assertTrue(searchBookResponse.hasErrors());
        assertThat(searchBookResponse.getErrorList().size()).isEqualTo(1);
    }

    @Test
    public void testResponseValidationError() {
        Ordering ordering = new Ordering("Title", "ASC");
        Paging paging = new Paging(1, 1);
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", ordering, paging);

        given(searchBookRequestValidator.validate(searchBookRequest)).willReturn(new ArrayList<>());
        List<CoreError> errorList = Collections.singletonList(new CoreError("Database", "Database does not contain books with this author/title"));
        given(database.searchBookByTitle("Title")).willReturn(new ArrayList<>());
        given(searchBookResponseValidator.validate(new ArrayList<>())).willReturn(errorList);

        SearchBookResponse searchBookResponse = searchBooksService.execute(searchBookRequest);

        assertTrue(searchBookResponse.hasErrors());
        assertThat(searchBookResponse.getErrorList().size()).isEqualTo(1);
    }

    @Test
    public void testBooksAreFoundTitleAsc() {
        ReflectionTestUtils.setField(searchBooksService, "orderingEnabled", true);
        ReflectionTestUtils.setField(searchBooksService, "pagingEnabled", true);
        Ordering ordering = new Ordering("Title", "ASC");
        Paging paging = new Paging(1, 2);
        SearchBookRequest searchBookRequest = new SearchBookRequest( null, "Title", ordering, paging);

        given(searchBookRequestValidator.validate(searchBookRequest)).willReturn(new ArrayList<>());
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Title", "Author"));
        bookList.add(new Book(1L, "A story", "Bobby"));
        given(database.searchBookByTitle("Title")).willReturn(bookList);
        given(searchBookResponseValidator.validate(bookList)).willReturn(new ArrayList<>());

        SearchBookResponse searchBookResponse = searchBooksService.execute(searchBookRequest);

        assertFalse(searchBookResponse.hasErrors());
        assertThat(searchBookResponse.getBookList().size()).isEqualTo(2);
        assertThat(searchBookResponse.getBookList().get(0).getTitle()).isEqualTo("A story");
        assertThat(searchBookResponse.getBookList().get(1).getTitle()).isEqualTo("Title");
    }

}
