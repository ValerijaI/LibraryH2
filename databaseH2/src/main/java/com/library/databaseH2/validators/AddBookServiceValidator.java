package com.library.databaseH2.validators;

import com.library.databaseH2.domain.Book;
import com.library.databaseH2.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddBookServiceValidator {

    public List<CoreError> validate(Book newBook, List<Book> bookList) {
        List<CoreError> errorList = new ArrayList<>();
        validateDuplicates(newBook, bookList).ifPresent(errorList::add);
        return errorList;
    }

    private Optional<CoreError> validateDuplicates(Book newBook, List<Book> bookList) {
        return bookList.stream().filter(book -> book.equals(newBook)).findAny().isPresent()
                ? Optional.of(new CoreError("Database", "Database contains the same book!"))
                : Optional.empty();
    }
}
