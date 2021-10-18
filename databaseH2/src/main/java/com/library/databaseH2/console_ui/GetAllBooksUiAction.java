package com.library.databaseH2.console_ui;

import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.services.GetAllBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllBooksUiAction implements UIAction {

    @Autowired
    private GetAllBooksService getAllBooksService;

    @Override
    public void execute() {
        GetAllBooksRequest getAllBooksRequest = new GetAllBooksRequest();
        GetAllBooksResponse getAllBooksResponse = getAllBooksService.execute(getAllBooksRequest);

        if (getAllBooksResponse.hasErrors()) {
            getAllBooksResponse.getErrorList().forEach(System.out::println);
        } else {
            System.out.println("Books:\n");
            getAllBooksResponse.getBookList().forEach(System.out::println);
        }
    }
}
