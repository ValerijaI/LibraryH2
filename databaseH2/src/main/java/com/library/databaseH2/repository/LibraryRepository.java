package com.library.databaseH2.repository;

import com.library.databaseH2.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LibraryRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByAuthorAndTitle(String author, String title);
}
