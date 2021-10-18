package com.library.databaseH2.console_ui;

import com.library.databaseH2.requests.Ordering;
import com.library.databaseH2.requests.Paging;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.SearchBookResponse;
import com.library.databaseH2.services.SearchBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class SearchBooksUiAction implements UIAction {

    @Autowired
    private SearchBooksService searchBooksService;

    @Override
    public void execute() {
        Scanner input = new Scanner(System.in);

        System.out.println("Please input author for search or press Enter");
        String author = input.nextLine();

        System.out.println("Please input title for search or press Enter");
        String title = input.nextLine();

        System.out.println("Please input ordering field: Author or Title");
        String orderBy = input.nextLine();

        System.out.println("Please input ordering direction: ASC or DESC");
        String orderDirection = input.nextLine();

        System.out.println("Please input page number");
        Integer pageNumber = input.nextInt();

        System.out.println("Please input page size");
        Integer pageSize = input.nextInt();

        Ordering ordering = new Ordering(orderBy, orderDirection);
        Paging paging = new Paging(pageNumber, pageSize);

        SearchBookRequest searchBookRequest = new SearchBookRequest(author, title, ordering, paging);
        SearchBookResponse searchBookResponse = searchBooksService.execute(searchBookRequest);

        if (searchBookResponse.hasErrors()) {
            searchBookResponse.getErrorList().forEach(System.out::println);
        } else {
            System.out.println("Books:\n");
            searchBookResponse.getBookList().forEach(System.out::println);
        }
    }
}
