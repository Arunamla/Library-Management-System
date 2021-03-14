package com.capestart.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capestart.entity.Book;
import com.capestart.entity.User;
import com.capestart.repository.UserRepository;
import com.capestart.service.BookService;
import com.capestart.service.UserDetailsServiceImpl;
import com.capestart.utility.Utility;

@Controller
@RequestMapping("/")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String viewbookList(Model model) {
		List<Book> listbooks = bookService.listAll();
		model.addAttribute("listbooks", listbooks);
		
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewbookForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		
		return "new_book";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savebook(@ModelAttribute("book") Book book, @RequestParam("imagefile") MultipartFile file) {
		
		if(file != null) {
			Byte[] byteObjects = Utility.convertToBytes(file);
			
			if(byteObjects != null) {
				book.setBookImage(byteObjects);
			}
		}
		
		bookService.save(book);
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditbookForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_book");
		
		Book book = bookService.get(id);
		mav.addObject("book", book);
		
		return mav;
	}	
	
	@RequestMapping("/delete/{id}")
	public String deletebook(@PathVariable(name = "id") Long id) {
		bookService.delete(id);	
		return "redirect:/";
	}
	
	@RequestMapping("/getBook/{id}/{userName}")
	public String getTheBook(@PathVariable(name = "id") Long id, @PathVariable(name = "userName") String userName) {
		
		User user = userRepository.findByUsername(userName);
		Book book = bookService.get(id);
		
		book.setBookLentUserId(user.getId());
		
		bookService.save(book);
		
		return "redirect:/";
	}
	
	@RequestMapping("/submitBook/{id}")
	public String submitTheBook(@PathVariable(name = "id") Long id) {
		Book book = bookService.get(id);
		
		book.setBookLentUserId(null);
		
		bookService.save(book);
		
		return "redirect:/";
	}
	 
	 
	 
	 @RequestMapping("{id}/bookImage")
	 public void renderImageFromDb(@PathVariable Long id, HttpServletResponse response) throws IOException {
	     Book book = bookService.get(id);
	     byte[] byteArray = new byte[book.getBookImage().length];

	     int i = 0;
	     for (Byte wrappedByte: book.getBookImage()) {
	         byteArray[i++] = wrappedByte; 
	     }

	     response.setContentType("image/jpeg");
	     InputStream is = new ByteArrayInputStream(byteArray);
	     IOUtils.copy(is, response.getOutputStream());
	 }
	
}
