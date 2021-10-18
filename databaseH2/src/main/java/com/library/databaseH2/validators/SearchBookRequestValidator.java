package com.library.databaseH2.validators;

import com.library.databaseH2.requests.Ordering;
import com.library.databaseH2.requests.Paging;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.CoreError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SearchBookRequestValidator {

    public List<CoreError> validate(SearchBookRequest searchBookRequest) {
        List<CoreError> errorList = new ArrayList<>();
        validateTitleAndAuthor(searchBookRequest).ifPresent(errorList::add);
        errorList.addAll(validateOrdering(searchBookRequest.getOrdering()));
        errorList.addAll(validatePaging(searchBookRequest.getPaging()));
        return errorList;
    }

    private Optional<CoreError> validateTitleAndAuthor(SearchBookRequest searchBookRequest) {
        return (searchBookRequest.getTitle() == null || searchBookRequest.getTitle().isEmpty())
                && (searchBookRequest.getAuthor() == null || searchBookRequest.getAuthor().isEmpty())
                ? Optional.of(new CoreError("title/author", "Search parameters can't be empty"))
                : Optional.empty();
    }

    private List<CoreError> validateOrdering(Ordering ordering) {
        List<CoreError> errorList = new ArrayList<>();
        validateOrderingIsNotProvided(ordering).ifPresent(errorList::add);
        if (!orderingIsNotProvided(ordering)) {
            validateOrderingOrderBy(ordering).ifPresent(errorList::add);
            validateOrderingOrderDirection(ordering).ifPresent(errorList::add);
        }
        return errorList;
    }

    private List<CoreError> validatePaging(Paging paging) {
        List<CoreError> errorList = new ArrayList<>();
        validatePagingIsNotProvided(paging).ifPresent(errorList::add);
        if (!pagingIsNotProvided(paging)) {
            validatePagingPageNumber(paging).ifPresent(errorList::add);
            validatePagingPageSize(paging).ifPresent(errorList::add);
        }
        return errorList;
    }

    private boolean orderingIsNotProvided(Ordering ordering) {
        return ordering == null || ordering.getOrderBy() == null || ordering.getOrderBy().isEmpty()
                || ordering.getDirection() == null || ordering.getDirection().isEmpty();
    }

    private Optional<CoreError> validateOrderingIsNotProvided(Ordering ordering) {
        return orderingIsNotProvided(ordering)
                ? Optional.of(new CoreError("Ordering", "Ordering is required!"))
                : Optional.empty();
    }

    private Optional<CoreError> validateOrderingOrderBy(Ordering ordering) {
        return !ordering.getOrderBy().equalsIgnoreCase("Title") && !ordering.getOrderBy().equalsIgnoreCase("Author")
                ? Optional.of(new CoreError("Order by", "Not valid order by parameter!"))
                : Optional.empty();
    }

    private Optional<CoreError> validateOrderingOrderDirection(Ordering ordering) {
        return !ordering.getDirection().equalsIgnoreCase("ASC") && !ordering.getDirection().equalsIgnoreCase("DESC")
                ? Optional.of(new CoreError("Order direction", "Not valid order direction parameter!"))
                : Optional.empty();
    }

    private boolean pagingIsNotProvided(Paging paging) {
        return paging == null || paging.getPageNumber() == null ||paging.getPageSize() == null;
    }

    private Optional<CoreError> validatePagingIsNotProvided(Paging paging) {
        return pagingIsNotProvided(paging)
                ? Optional.of(new CoreError("Paging", "Paging is required!"))
                : Optional.empty();
    }

    private Optional<CoreError> validatePagingPageNumber(Paging paging) {
        return  paging.getPageNumber() < 0
                ? Optional.of(new CoreError("Paging", "Page number is not valid!"))
                : Optional.empty();
    }

    private Optional<CoreError> validatePagingPageSize(Paging paging) {
        return  paging.getPageSize() < 0
                ? Optional.of(new CoreError("Paging", "Page size is not valid!"))
                : Optional.empty();
    }
}
