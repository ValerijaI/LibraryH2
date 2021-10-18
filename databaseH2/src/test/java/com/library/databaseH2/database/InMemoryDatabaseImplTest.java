package com.library.databaseH2.database;

import com.library.databaseH2.domain.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryDatabaseImplTest {

    @Test
    public void testAddBookTitle() {
        Database database = new InMemoryDatabaseImpl();
        database.addBook(new Book(1L, "Title", "Author"));

        assertThat(database.getBooks().size()).isEqualTo(1);
        assertThat(database.getBooks().get(0).getTitle()).isEqualTo("Title");
    }

    @Test
    public void testAddBookAuthor() {
        Database database = new InMemoryDatabaseImpl();
        database.addBook(new Book(1L, "Title", "Author"));

        assertThat(database.getBooks().size()).isEqualTo(1);
        assertThat(database.getBooks().get(0).getAuthor()).isEqualTo("Author");
    }

    @Test
    public void testDeleteBookWithExistingId() {
        Database database = new InMemoryDatabaseImpl();
        database.addBook(new Book(1L, "Title", "Author"));

        database.deleteBookById(1L);

        assertTrue(database.getBooks().isEmpty());
    }

    @Test
    public void testDeleteBookWithNotExistingId() {
        Database database = new InMemoryDatabaseImpl();
        database.addBook(new Book(1L, "Title", "Author"));

        database.deleteBookById(5L);

        assertFalse(database.getBooks().isEmpty());
    }

    @Test
    public void testGetAllBooks() {
        Database database = new InMemoryDatabaseImpl();
        database.addBook(new Book(1L, "Title", "Author"));

        List<Book>bookList = database.getBooks();

        assertThat(bookList.size()).isEqualTo(1);
    }

    @Test
    public void testGetAllBooksEmptyDatabase() {
        Database database = new InMemoryDatabaseImpl();

        List<Book>bookList = database.getBooks();

        assertTrue(bookList.isEmpty());
    }
}