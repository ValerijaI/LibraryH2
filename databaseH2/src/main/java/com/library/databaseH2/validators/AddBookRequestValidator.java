package com.library.databaseH2.validators;

import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.requests.AddBookRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddBookRequestValidator {

    public List<CoreError> validate (AddBookRequest addBookRequest) {
        List<CoreError> errorList = new ArrayList<>();
        validateTitle(addBookRequest).ifPresent(errorList::add);
        validateAuthor(addBookRequest).ifPresent(errorList::add);

        return errorList;
    }

    private Optional<CoreError> validateAuthor(AddBookRequest addBookRequest) {
        return (addBookRequest.getAuthor() == null || addBookRequest.getAuthor().isEmpty())
                ? Optional.of(new CoreError("Author", "Author can't be empty"))
                : Optional.empty();
    }

    private Optional<CoreError> validateTitle(AddBookRequest addBookRequest) {
        return  (addBookRequest.getTitle() == null || addBookRequest.getTitle().isEmpty())
                ? Optional.of(new CoreError("Title", "Title can't be empty"))
                : Optional.empty();
    }
}
