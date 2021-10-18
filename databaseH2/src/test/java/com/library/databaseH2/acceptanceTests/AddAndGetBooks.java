package com.library.databaseH2.acceptanceTests;

import com.library.databaseH2.config.BookListConfiguration;
import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.services.AddBookService;
import com.library.databaseH2.services.GetAllBooksService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

public class AddAndGetBooks {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(BookListConfiguration.class);
    }

    @Test
    public void test() {

        AddBookRequest addBookRequest = new AddBookRequest("Title1", "Author1");
        getAddBookService().execute(addBookRequest);

        addBookRequest = new AddBookRequest("Title2", "Author2");
        getAddBookService().execute(addBookRequest);

        addBookRequest = new AddBookRequest("Title3", "Author3");
        getAddBookService().execute(addBookRequest);

        GetAllBooksRequest getAllBooksRequest = new GetAllBooksRequest();
        GetAllBooksResponse getAllBooksResponse = getAllBookService().execute(getAllBooksRequest);

        assertThat(getAllBooksResponse.getBookList().size()).isEqualTo(3);
        assertFalse(getAllBooksResponse.hasErrors());
    }

    private AddBookService getAddBookService() {
        return applicationContext.getBean(AddBookService.class);
    }

    private GetAllBooksService getAllBookService() {
        return applicationContext.getBean(GetAllBooksService.class);
    }
}
