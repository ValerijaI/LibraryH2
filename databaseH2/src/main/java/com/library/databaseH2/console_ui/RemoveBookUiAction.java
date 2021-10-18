package com.library.databaseH2.console_ui;

import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.RemoveBookResponse;
import com.library.databaseH2.services.RemoveBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class RemoveBookUiAction implements UIAction {

    @Autowired
    private RemoveBookService removeBookService;

    @Override
    public void execute() {
        System.out.println("Please input book's id:\n");
        Long bookId = new Scanner(System.in).nextLong();
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(bookId);
        RemoveBookResponse removeBookResponse = removeBookService.execute(removeBookRequest);

        if (removeBookResponse.hasErrors()) {
            removeBookResponse.getErrorList().forEach(System.out::println);
        } else {
            System.out.format("Book with id %d deleted!%n", bookId);
        }
    }
}
