package com.library.databaseH2.services;

import com.library.databaseH2.database.Database;
import com.library.databaseH2.domain.Book;
import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.responses.AddBookResponse;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.validators.AddBookRequestValidator;
import com.library.databaseH2.validators.AddBookServiceValidator;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddBookServiceTest {

    @Mock
    private Database database;

    @Mock
    private AddBookRequestValidator addBookRequestValidator;

    @Mock
    private AddBookServiceValidator addBookServiceValidator;

    @InjectMocks
    private AddBookService addBookService;

    @Test
    public void testBookAdded() {
        AddBookRequest addBookRequest = new AddBookRequest("Title", "Author");
        given(addBookRequestValidator.validate(addBookRequest)).willReturn(new ArrayList<>());
        given(addBookServiceValidator.validate(any(), any())).willReturn(new ArrayList<>());

        AddBookResponse addBookResponse = addBookService.execute(addBookRequest);

        assertFalse(addBookResponse.hasErrors());
        assertThat(addBookResponse.getBook()).isEqualTo(new Book(1L, "Title", "Author"));
    }

    @Test
    public void testRequestValidationError() {
        AddBookRequest addBookRequest = new AddBookRequest("", "Author");
        CoreError expectedError = new CoreError("Title", "Title can't be empty");
        List<CoreError> errorList = Collections.singletonList(expectedError);
        given(addBookRequestValidator.validate(addBookRequest)).willReturn(errorList);

        AddBookResponse addBookResponse = addBookService.execute(addBookRequest);

        assertTrue(addBookResponse.hasErrors());
        assertThat(addBookResponse.getErrorList().get(0)).isEqualTo(expectedError);

        verifyNoInteractions(database);
        verifyNoInteractions(addBookServiceValidator);
    }

    @Test
    public void testResponseValidationError() {
        AddBookRequest addBookRequest = new AddBookRequest("", "Author");
        CoreError expectedError = new CoreError("Database", "Database contains the same book!");
        List<CoreError> errorList = Collections.singletonList(expectedError);
        given(addBookRequestValidator.validate(addBookRequest)).willReturn(new ArrayList<>());
        given(addBookServiceValidator.validate(any(), any())).willReturn(errorList);

        AddBookResponse addBookResponse = addBookService.execute(addBookRequest);

        assertTrue(addBookResponse.hasErrors());
        assertThat(addBookResponse.getErrorList().get(0)).isEqualTo(expectedError);
    }
}