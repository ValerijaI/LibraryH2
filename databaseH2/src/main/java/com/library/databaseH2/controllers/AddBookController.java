package com.library.databaseH2.controllers;

import com.library.databaseH2.requests.AddBookRequest;
import com.library.databaseH2.responses.AddBookResponse;
import com.library.databaseH2.services.AddBookService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
@Log
public class AddBookController {

    private final AddBookService addBookService;

    @PostMapping("/add")
    public AddBookResponse addBook(@RequestBody AddBookRequest addBookRequest) {
            log.info("author " + addBookRequest.getAuthor());
            log.info("title " + addBookRequest.getTitle());
            return addBookService.execute(addBookRequest);
        }

    }