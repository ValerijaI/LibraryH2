package com.library.databaseH2.validators;

import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.GetAllBooksResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GetAllBooksResponseValidator {

    public List<CoreError> validate(GetAllBooksResponse getAllBooksResponse) {
        List<CoreError> errorList = new ArrayList<>();
        validateSize(getAllBooksResponse).ifPresent(errorList::add);
        return errorList;
    }

    private Optional<CoreError> validateSize(GetAllBooksResponse getAllBooksResponse) {
        return getAllBooksResponse.getBookList().isEmpty()
                ? Optional.of(new CoreError("Database", "Database is empty"))
                : Optional.empty();
    }

}
