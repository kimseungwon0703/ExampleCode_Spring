package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.answer.answerEntity;
import com.example.demo.user.userEntity;
import com.example.demo.user.userEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; 

@Entity
public class questionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
    private List<answerEntity> answerList;
    
    private LocalDateTime modifyDate;

	@ManyToOne
    private userEntity author;

    //Getter, Setter Setting
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public List<answerEntity> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<answerEntity> answerList) {
		this.answerList = answerList;
	} 
	
	public userEntity getAuthor() {
		return author;
	}
	
	public void setAuthor(userEntity author) {
		this.author = author;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}
}