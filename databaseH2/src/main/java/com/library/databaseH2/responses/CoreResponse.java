package com.library.databaseH2.responses;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreResponse {

    private List<CoreError> errorList = new ArrayList<>();

    public CoreResponse(List<CoreError> errorList) {
        this.errorList = errorList;
    }

    public CoreResponse() {}

    public List<CoreError> getErrorList() {
        return errorList;
    }

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }
}
