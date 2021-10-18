package com.library.databaseH2.services;

import com.library.databaseH2.database.Database;
import com.library.databaseH2.domain.Book;
import com.library.databaseH2.entity.BookConverter;
import com.library.databaseH2.repository.LibraryRepository;
import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.validators.GetAllBooksResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllBooksService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private GetAllBooksResponseValidator getAllBooksResponseValidator;

    @Autowired
    private BookConverter bookConverter;

    public GetAllBooksResponse execute(GetAllBooksRequest getAllBooksRequest) {

        List <Book> bookList = libraryRepository.findAll()
                .stream()
                .map(bookEntity -> bookConverter.convertEntityToDomain(bookEntity))
                .collect(Collectors.toList());

        GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse(bookList);

        List<CoreError> errorList = getAllBooksResponseValidator.validate(getAllBooksResponse);

        return errorList.isEmpty()
                ? getAllBooksResponse
                : new GetAllBooksResponse(new ArrayList<>(), errorList);
    }
}
