package com.library.databaseH2.services;

import com.library.databaseH2.entity.BookConverter;
import com.library.databaseH2.entity.BookEntity;
import com.library.databaseH2.repository.LibraryRepository;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.database.Database;
import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.RemoveBookResponse;
import com.library.databaseH2.validators.RemoveBookRequestValidator;
import com.library.databaseH2.validators.RemoveBookResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoveBookService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private RemoveBookRequestValidator removeBookRequestValidator;

    @Autowired
    private RemoveBookResponseValidator removeBookResponseValidator;

    @Autowired
    private BookConverter bookConverter;

    public RemoveBookResponse execute(RemoveBookRequest removeBookRequest) {
        List<CoreError> errorList = removeBookRequestValidator.validate(removeBookRequest);

        if (!errorList.isEmpty()) {
            return new RemoveBookResponse(errorList);
        }
        BookEntity book = libraryRepository.getById(removeBookRequest.getId());
        boolean isBookDeleted = book != null;

        errorList = removeBookResponseValidator.validate(isBookDeleted);

        if (!errorList.isEmpty()) {
            return new RemoveBookResponse(errorList);
        }
        libraryRepository.delete(book);
        return new RemoveBookResponse(removeBookRequest.getId());
    }
}
