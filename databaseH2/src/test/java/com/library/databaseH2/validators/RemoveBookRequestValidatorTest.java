package com.library.databaseH2.validators;

import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.CoreError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RemoveBookRequestValidatorTest {

    @Test
    public void testValidId() {
        RemoveBookRequestValidator removeBookRequestValidator = new RemoveBookRequestValidator();

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(5L);
        List<CoreError> errorList = removeBookRequestValidator.validate(removeBookRequest);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testIdIsNull() {
        RemoveBookRequestValidator removeBookRequestValidator = new RemoveBookRequestValidator();

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(null);
        List<CoreError> errorList = removeBookRequestValidator.validate(removeBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("id");
    }

    @Test
    public void testIdIsNegative() {
        RemoveBookRequestValidator removeBookRequestValidator = new RemoveBookRequestValidator();

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(-8L);
        List<CoreError> errorList = removeBookRequestValidator.validate(removeBookRequest);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("id");
    }
}