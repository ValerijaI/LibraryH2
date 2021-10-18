package com.library.databaseH2.services;

import com.library.databaseH2.database.Database;
import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.RemoveBookResponse;
import com.library.databaseH2.validators.RemoveBookRequestValidator;
import com.library.databaseH2.validators.RemoveBookResponseValidator;
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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RemoveBookServiceTest {

    @Mock
    private Database database;

    @Mock
    private RemoveBookRequestValidator removeBookRequestValidator;

    @Mock
    private RemoveBookResponseValidator removeBookResponseValidator;

    @InjectMocks
    private RemoveBookService removeBookService;

    @Test
    public void testBookRemoved() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(1L);
        given(removeBookRequestValidator.validate(removeBookRequest)).willReturn(new ArrayList<>());
        given(database.deleteBookById(1L)).willReturn(true);
        given(removeBookResponseValidator.validate(anyBoolean())).willReturn(new ArrayList<>());

        RemoveBookResponse removeBookResponse = removeBookService.execute(removeBookRequest);

        assertFalse(removeBookResponse.hasErrors());
        assertThat(removeBookResponse.getId()).isEqualTo(1L);
    }

    @Test
    public void testRequestValidationError() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(-1L);
        List<CoreError> errorList = Collections.singletonList(new CoreError("id", "Id can't be 0 or negative!"));
        given(removeBookRequestValidator.validate(removeBookRequest)).willReturn(errorList);

        RemoveBookResponse removeBookResponse = removeBookService.execute(removeBookRequest);

        assertTrue(removeBookResponse.hasErrors());
        assertThat(removeBookResponse.getErrorList().size()).isEqualTo(1);

        verifyNoInteractions(database);
        verifyNoInteractions(removeBookResponseValidator);
    }

    @Test
    public void testResponseValidationError() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(1L);
        given(removeBookRequestValidator.validate(removeBookRequest)).willReturn(new ArrayList<>());
        given(database.deleteBookById(1L)).willReturn(false);
        List<CoreError> errorList = Collections.singletonList(new CoreError("id", "Database doe not contain book with this id"));

        given(removeBookResponseValidator.validate(anyBoolean())).willReturn(errorList);

        RemoveBookResponse removeBookResponse = removeBookService.execute(removeBookRequest);

        assertTrue(removeBookResponse.hasErrors());
        assertThat(removeBookResponse.getErrorList().size()).isEqualTo(1);
    }
}