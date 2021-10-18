package com.library.databaseH2.validators;

import com.library.databaseH2.requests.Ordering;
import com.library.databaseH2.requests.Paging;
import com.library.databaseH2.requests.SearchBookRequest;
import com.library.databaseH2.responses.CoreError;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SearchBookRequestValidatorTest {

    private static final Ordering VALID_ORDERING_TITLE_ASC = new Ordering("Title", "ASC");
    private static final Ordering UNVALID_ORDERING_EMPTY_BOTH = new Ordering(null, "");
    private static final Ordering UNVALID_ORDERING_EMPTY_ORDER_BY = new Ordering(null, "ASC");

    private static final Paging VALID_PAGING_1_1 = new Paging(1, 1);
    private static final Paging UNVALID_PAGING = new Paging(null, null);

    private SearchBookRequestValidator searchBookRequestValidator;

    @Before
    public void setUp() {
        searchBookRequestValidator = new SearchBookRequestValidator();
    }

    @Test
    public void testValidRequest() {
        SearchBookRequest searchBookRequest = new SearchBookRequest("Author", "Title", VALID_ORDERING_TITLE_ASC, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testValidRequestFilledAuthor() {
        SearchBookRequest searchBookRequest = new SearchBookRequest("Author", "", VALID_ORDERING_TITLE_ASC, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void testValidRequestFilledTitle() {
        SearchBookRequest searchBookRequest = new SearchBookRequest("", "Title", VALID_ORDERING_TITLE_ASC, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        assertTrue(errorList.isEmpty());
    }

    @Test
    public void TestEmptyAuthorAndTitle() {
        SearchBookRequest searchBookRequest = new SearchBookRequest("", null, VALID_ORDERING_TITLE_ASC, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("title/author", "Search parameters can't be empty");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestNullOrdering() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", null, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Ordering", "Ordering is required!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestEmptyOrderingParameters() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", UNVALID_ORDERING_EMPTY_BOTH, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("title/author", "Search parameters can't be empty");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestEmptyOrderingParameter() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", UNVALID_ORDERING_EMPTY_ORDER_BY, VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("title/author", "Search parameters can't be empty");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestOrderingNotValidOrderBy() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", new Ordering("Tutle", "DESC"), VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Order by", "Not valid order by parameter!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestOrderingNotValidOrderDirection() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", new Ordering("Title", "MINI"), VALID_PAGING_1_1);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Order direction", "Not valid order direction parameter!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestNullPaging() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", VALID_ORDERING_TITLE_ASC, null);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Paging", "Paging is required!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestEmptyPagingParameters() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", VALID_ORDERING_TITLE_ASC, UNVALID_PAGING);
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Paging", "Paging is required!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestEmptyPagingParameter() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", VALID_ORDERING_TITLE_ASC, new Paging(-1, 5));
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Paging", "Page number is not valid!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestPagingNotValidPageNumber() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", VALID_ORDERING_TITLE_ASC, new Paging(-9, 5));
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Paging", "Page number is not valid!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }

    @Test
    public void TestPagingNotValidPageSize() {
        SearchBookRequest searchBookRequest = new SearchBookRequest(null, "Title", VALID_ORDERING_TITLE_ASC, new Paging(1, -85));
        List<CoreError> errorList = searchBookRequestValidator.validate(searchBookRequest);

        CoreError expectedError = new CoreError("Paging", "Page size is not valid!");
        assertThat(errorList.size()).isEqualTo(1);
        assertThat(errorList.contains(expectedError));
    }
}