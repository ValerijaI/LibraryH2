package com.library.databaseH2.validators;

import com.library.databaseH2.domain.Book;
import com.library.databaseH2.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SearchBookResponseValidator {

    public List<CoreError> validate(List<Book> bookList) {
        List<CoreError> errorList = new ArrayList<>();
        validateSize(bookList).ifPresent(errorList::add);
        return errorList;
    }

    private Optional<CoreError> validateSize(List<Book>bookList) {
        return bookList.isEmpty()
                ? Optional.of(new CoreError("Database", "Database does not contain books with this author/title"))
                : Optional.empty();
    }
}
