package com.library.databaseH2.validators;

import com.library.databaseH2.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RemoveBookResponseValidator {

    public List<CoreError> validate (boolean isDeleted) {
        return !isDeleted
                ? Collections.singletonList(new CoreError("id", "Database does not contain book with this id"))
                : new ArrayList<>();
    }
}
