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

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteNotExistingBooks {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(BookListConfiguration.class);
    }

    @Test
    public void test() {
        AddBookRequest addBookRequest = new AddBookRequest("Title", "Author");
        getAddBookService().execute(addBookRequest);

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(2L);
        getRemoveBookService().execute(removeBookRequest);

        removeBookRequest = new RemoveBookRequest(5L);
        getRemoveBookService().execute(removeBookRequest);

        GetAllBooksRequest getAllBooksRequest = new GetAllBooksRequest();
        GetAllBooksResponse getAllBooksResponse = getGetAllBookService().execute(getAllBooksRequest);
        assertThat(getAllBooksResponse.getBookList().size()).isEqualTo(1);
    }

    private AddBookService getAddBookService() {
        return applicationContext.getBean(AddBookService.class);
    }

    private RemoveBookService getRemoveBookService() {
        return applicationContext.getBean(RemoveBookService.class);
    }

    private GetAllBooksService getGetAllBookService() {
        return applicationContext.getBean(GetAllBooksService.class);
    }
}
