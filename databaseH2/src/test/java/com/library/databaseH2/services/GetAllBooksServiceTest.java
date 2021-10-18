package com.library.databaseH2.services;

import com.library.databaseH2.database.Database;
import com.library.databaseH2.domain.Book;
import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.validators.GetAllBooksResponseValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetAllBooksServiceTest {

    @Mock
    private Database database;

    @Mock
    private GetAllBooksResponseValidator getAllBooksResponseValidator;

    @InjectMocks
    private GetAllBooksService getAllBooksService;

    @Test
    public void testBooksReceived() {
        List<Book> bookList = Collections.singletonList(new Book(1L, "Title", "Author"));
        given(database.getBooks()).willReturn(bookList);
        given(getAllBooksResponseValidator.validate(any())).willReturn(new ArrayList<>());

        GetAllBooksResponse getAllBooksResponse = getAllBooksService.execute(new GetAllBooksRequest());

        assertFalse(getAllBooksResponse.hasErrors());
        assertThat(getAllBooksResponse.getBookList().size()).isEqualTo(1);
    }

    @Test
    public void testEmptyDatabase() {
        given(database.getBooks()).willReturn(new ArrayList<>());
        List<CoreError> errorList = Collections.singletonList(new CoreError("Database", "Database is empty"));
        given(getAllBooksResponseValidator.validate(any())).willReturn(errorList);
        GetAllBooksResponse getAllBooksResponse = getAllBooksService.execute(new GetAllBooksRequest());

        assertTrue(getAllBooksResponse.hasErrors());
        assertThat(getAllBooksResponse.getErrorList().size()).isEqualTo(1);
        assertThat(getAllBooksResponse.getErrorList()).isEqualTo(errorList);
    }
}