package com.capestart.entity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Book {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String authorName;
	private String bookName;
	private String publisherName;
	private String category;
	private Long bookLentUserId;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private Byte[] bookImage;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookLentUserId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

}
