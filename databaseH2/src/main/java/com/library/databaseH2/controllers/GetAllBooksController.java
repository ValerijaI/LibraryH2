package com.library.databaseH2.controllers;

import com.library.databaseH2.requests.GetAllBooksRequest;
import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.GetAllBooksResponse;
import com.library.databaseH2.responses.RemoveBookResponse;
import com.library.databaseH2.services.GetAllBooksService;
import com.library.databaseH2.services.RemoveBookService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
@Log
public class GetAllBooksController {

    private final GetAllBooksService getAllBooksService;

    @GetMapping("/getAll")
    public GetAllBooksResponse getAllBooks() {
            log.info("Get all controller");
            return getAllBooksService.execute(new GetAllBooksRequest());
        }

    }