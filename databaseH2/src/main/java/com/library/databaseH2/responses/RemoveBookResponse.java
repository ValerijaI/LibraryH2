package com.library.databaseH2.responses;

import java.util.List;

public class RemoveBookResponse extends CoreResponse {

    private Long id;

    public RemoveBookResponse(Long id) {
        this.id = id;
    }

    public RemoveBookResponse(List<CoreError> errorList) {
        super(errorList);
    }

    public Long getId() {
        return id;
    }
}
