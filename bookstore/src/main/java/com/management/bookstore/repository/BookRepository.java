package com.management.bookstore.repository;

import com.management.bookstore.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Books,String> {
    public List<Books> findAll();
    //public boolean deleteBooksById(String id);
    public Optional<Books> findByTitleAndAuthor(String title, String author);
    public Optional<Books> findByISBN(String isbn);
    Page<Books> findAll(Pageable pageable);
    @Query("SELECT b FROM Books b WHERE " +
            "LOWER(b.ISBN) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Books> browseBooks(@Param("searchTerm") String searchTerm, Pageable pageable);
}
