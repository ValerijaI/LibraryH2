package com.library.databaseH2.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookRequest {

    private String title;
    private String author;
    private Ordering ordering;
    private Paging paging;
}
