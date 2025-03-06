package com.example.demo.answer;

import java.time.LocalDateTime;

import com.example.demo.question.questionEntity;
import com.example.demo.user.userEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class answerEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate; 

    @ManyToOne
    private questionEntity question;
    
    @ManyToOne
    private userEntity author;
    
    private LocalDateTime modifyDate;

	public userEntity getAuthor() {
		return author;
	}

	public void setAuthor(userEntity author) {
		this.author = author;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public questionEntity getQuestion() {
		return question;
	}

	public void setQuestion(questionEntity question) {
		this.question = question;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

    //Getter and Setter Setting
	

    
}
