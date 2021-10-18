package com.library.databaseH2.validators;

import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.requests.RemoveBookRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RemoveBookRequestValidator {

    public List<CoreError> validate (RemoveBookRequest removeBookRequest) {
        List<CoreError> errorList = new ArrayList<>();

        validateId(removeBookRequest).ifPresent(errorList::add);
        return errorList;
    }

    private Optional<CoreError> validateId(RemoveBookRequest removeBookRequest) {
        return (removeBookRequest.getId() == null ||removeBookRequest.getId() <= 0)
                ? Optional.of(new CoreError("id", "Id can't be 0 or negative!"))
                : Optional.empty();
    }
}
