package com.library.databaseH2.controllers;

import com.library.databaseH2.requests.RemoveBookRequest;
import com.library.databaseH2.responses.RemoveBookResponse;
import com.library.databaseH2.services.RemoveBookService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
@Log
public class RemoveBookController {

    private final RemoveBookService removeBookService;

    @DeleteMapping ("/remove")
    public RemoveBookResponse removeBooks(@RequestBody RemoveBookRequest removeBookRequest) {
            log.info("id " + removeBookRequest.getId());
            return removeBookService.execute(removeBookRequest);
        }

    }