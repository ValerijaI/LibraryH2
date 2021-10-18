package com.library.databaseH2.console_ui;

import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.responses.AddBookResponse;
import com.library.databaseH2.services.AddBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AddBookUiAction implements UIAction {

    @Autowired
    private AddBookService addBookService;

    @Override
    public void execute() {
        System.out.println("Please input title:");
        String title = new Scanner(System.in).nextLine();
        System.out.println("Please input author:");
        String author = new Scanner(System.in).nextLine();
        AddBookRequest addBookRequest = new AddBookRequest(title, author);
        AddBookResponse addBookResponse = addBookService.execute(addBookRequest);

        if (addBookResponse.hasErrors()) {
            System.out.println("Book can't be added");
            addBookResponse.getErrorList().forEach(System.out::println);
        } else {
            System.out.println("Book added"+ addBookResponse.getBook());
        }
    }
}
