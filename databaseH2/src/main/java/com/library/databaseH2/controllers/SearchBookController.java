package com.library.databaseH2.controllers;

import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.AddBookResponse;
import com.library.databaseH2.responses.SearchBookResponse;
import com.library.databaseH2.services.AddBookService;
import com.library.databaseH2.services.SearchBooksService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
@Log
public class SearchBookController {

    private final SearchBooksService searchBooksService;

    @PostMapping("/search")
    public SearchBookResponse searchBooks(@RequestBody SearchBookRequest searchBookRequest) {
            log.info("author " + searchBookRequest.getAuthor());
            log.info("title " + searchBookRequest.getTitle());
            log.info("ordered by " + searchBookRequest.getOrdering().getOrderBy());
            log.info("order direction " + searchBookRequest.getOrdering().getDirection());
            log.info("page number " + searchBookRequest.getPaging().getPageNumber());
            log.info("page size " + searchBookRequest.getPaging().getPageSize());
            return searchBooksService.execute(searchBookRequest);
        }

    }