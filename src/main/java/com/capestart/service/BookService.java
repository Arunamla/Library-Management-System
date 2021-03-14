package com.capestart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capestart.entity.Book;
import com.capestart.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository repo;
	
	public List<Book> listAll() {		
		return repo.findAll();
	}
	
	public void save(Book product) {
		repo.save(product);
	}
	
	public Book get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Transactional
	public void updateBookByLentUserId(Long id) {
		repo.updateBookByLentUserId(id);
	}
}
