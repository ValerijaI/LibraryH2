package com.library.databaseH2.validators;

import com.library.databaseH2.domain.Book;
import com.library.databaseH2.responses.CoreError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddBookServiceValidatorTest {

    @Test
    public void testNoDuplicates() {
        Book book = new Book(1L,"Title", "Author");
        List<Book> bookList = new ArrayList<>();

        AddBookServiceValidator addBookServiceValidator = new AddBookServiceValidator();

        List<CoreError> errorList = addBookServiceValidator.validate(book, bookList);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testHasDuplicateInDatabase() {
        Book book = new Book(1L, "Title", "Author");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        AddBookServiceValidator addBookServiceValidator = new AddBookServiceValidator();

        List<CoreError> errorList = addBookServiceValidator.validate(book, bookList);

        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.get(0).getField()).isEqualTo("Database");
    }

}