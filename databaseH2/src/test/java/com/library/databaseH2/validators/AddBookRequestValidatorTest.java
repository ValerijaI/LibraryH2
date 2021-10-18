package com.library.databaseH2.validators;

import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.responses.CoreError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddBookRequestValidatorTest {

    @Test
    public void testValidInput() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest("Title", "Author");
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testTitleNull() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest(null, "Author");
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("Title");
        assertThat(errorList.get(0).getMessage()).isEqualTo("Title can't be empty");
    }

    @Test
    public void testTitleIsEmpty() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest("", "Author");
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("Title");
        assertThat(errorList.get(0).getMessage()).isEqualTo("Title can't be empty");
    }

    @Test
    public void testAuthorIsNull() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest("Title", null);
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("Author");
        assertThat(errorList.get(0).getMessage()).isEqualTo("Author can't be empty");
    }

    @Test
    public void testAuthorIsEmpty() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest("Title", "");
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("Author");
        assertThat(errorList.get(0).getMessage()).isEqualTo("Author can't be empty");
    }

    @Test
    public void testAuthorAndTitleAreNull() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest(null, null);
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(2);
        assertTrue(errorList.stream().anyMatch(coreError -> coreError.getField().equals("Title")));
        assertTrue(errorList.stream().anyMatch(coreError -> coreError.getField().equals("Author")));
    }

    @Test
    public void testAuthorAndTitleAreEmpty() {
        AddBookRequestValidator addBookRequestValidator = new AddBookRequestValidator();

        AddBookRequest addBookRequest = new AddBookRequest("", "");
        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        assertThat(errorList.size()).isEqualTo(2);
        assertTrue(errorList.stream().anyMatch(coreError -> coreError.getField().equals("Title")));
        assertTrue(errorList.stream().anyMatch(coreError -> coreError.getField().equals("Author")));
    }

}