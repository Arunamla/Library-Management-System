package com.capestart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capestart.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	    @Modifying
	    @Query("update Book b set b.bookLentUserId = null where b.bookLentUserId = :bookLentUserId")
	    void updateBookByLentUserId(@Param("bookLentUserId") Long bookLentUserId);

}
