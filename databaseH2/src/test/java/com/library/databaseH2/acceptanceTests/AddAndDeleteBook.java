package com.library.databaseH2.acceptanceTests;

import com.library.databaseH2.config.BookListConfiguration;
import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.services.AddBookService;
import com.library.databaseH2.services.GetAllBooksService;
import com.library.databaseH2.services.RemoveBookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertTrue;

public class AddAndDeleteBook {

    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        applicationContext = new AnnotationConfigApplicationContext(BookListConfiguration.class);
    }

    @Test
    public void bookIsAddedAndDeleted() {
        AddBookRequest addBookRequest = new AddBookRequest("Title", "Author");
        getAddBookService().execute(addBookRequest);

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(1L);
        getRemoveBookService().execute(removeBookRequest);

        GetAllBooksRequest getAllBooksRequest = new GetAllBooksRequest();
        GetAllBooksResponse getAllBooksResponse = getGetBookService().execute(getAllBooksRequest);

        assertTrue(getAllBooksResponse.getBookList().isEmpty());
    }

    private AddBookService getAddBookService() {
        return applicationContext.getBean(AddBookService.class);
    }

    private RemoveBookService getRemoveBookService() {
        return applicationContext.getBean(RemoveBookService.class);
    }

    private GetAllBooksService getGetBookService() {
        return applicationContext.getBean(GetAllBooksService.class);
    }
}