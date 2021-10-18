package com.library.databaseH2.validators;

import com.library.databaseH2.domain.Book;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.GetAllBooksResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GetAllBooksResponseValidatorTest {

    @Test
    public void testDatabaseIsNotEmpty() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Title", "Author"));

        GetAllBooksResponseValidator getAllBooksResponseValidator = new GetAllBooksResponseValidator();

        GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse(bookList);
        List<CoreError> errorList = getAllBooksResponseValidator.validate(getAllBooksResponse);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testDatabaseIsEmpty() {
        List<Book> bookList = new ArrayList<>();

        GetAllBooksResponseValidator getAllBooksResponseValidator = new GetAllBooksResponseValidator();

        GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse(bookList);
        List<CoreError> errorList = getAllBooksResponseValidator.validate(getAllBooksResponse);

        assertThat(errorList.size()).isEqualTo(1);
    }

}