package com.library.databaseH2.services;

import com.library.databaseH2.entity.BookConverter;
import com.library.databaseH2.entity.BookEntity;
import com.library.databaseH2.repository.LibraryRepository;
import com.library.databaseH2.responses.CoreError;
import com.library.databaseH2.domain.Book;
import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.responses.AddBookResponse;
import com.library.databaseH2.validators.AddBookRequestValidator;
import com.library.databaseH2.validators.AddBookServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class AddBookService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private AddBookRequestValidator addBookRequestValidator;

    @Autowired
    private AddBookServiceValidator addBookServiceValidator;

    @Autowired
    private BookConverter bookConverter;

    public AddBookResponse execute(AddBookRequest addBookRequest) {

        List<CoreError> errorList = addBookRequestValidator.validate(addBookRequest);

        if (!errorList.isEmpty()) {
            return new AddBookResponse(errorList);
        }

        Book book = new Book(null, addBookRequest.getTitle(), addBookRequest.getAuthor());

        List <Book> bookList = libraryRepository.findAll()
                .stream()
                .map(bookEntity -> bookConverter.convertEntityToDomain(bookEntity))
                .collect(Collectors.toList());

        errorList = addBookServiceValidator.validate(book, bookList);
        if (!errorList.isEmpty()) {
            return new AddBookResponse(errorList);
        }

        BookEntity bookEntity = bookConverter.convertDomainToBookEntity(book);

        libraryRepository.save(bookEntity);
        book.setId(bookEntity.getId());
        return new AddBookResponse(book);
    }
}
