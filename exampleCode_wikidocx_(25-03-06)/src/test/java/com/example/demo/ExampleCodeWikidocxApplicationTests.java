package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.answer.AnswerRepo;
import com.example.demo.question.QuestionRepo;
import com.example.demo.question.questionService;

@SpringBootTest
class ExampleCodeWikidocxApplicationTests {

	
	@Autowired 
	private QuestionRepo QueRepo;
	  
	@Autowired 
	private AnswerRepo AnsRepo;

	@Autowired
	private questionService QueSer;

	/* @Transactional */
	/* transactional 데이터 롤백 */
	@Test
	void testJpa() {
		//JUnit 데이터 저장 테스트
		/*
		 * questionEntity q1 = new questionEntity(); q1.setSubject("sbb가 무엇인가요?");
		 * q1.setContent("sbb가 알고싶어요?"); q1.setCreateDate(LocalDateTime.now());
		 * this.QueRepo.save(q1);
		 */
		
		//findAll
//		List<questionEntity> all = this.QueRepo.findAll();
//		assertEquals(2, all.size());
//		
//		questionEntity q = all.get(0);
//		assertEquals("sbb가 알고싶어요?", q.getSubject());
		
		//findById
//		Optional<questionEntity> oq = this.QueRepo.findById(1);
//		if(oq.isPresent()) {
//			questionEntity q = oq.get();
//			assertEquals("sbb가 알고싶어요?", q.getSubject());
//		}
		
		//findBySubjcet - Repo Edit
//		questionEntity q = this.QueRepo.findBySubject("sbb가 알고싶어요?");
//		assertEquals(1, q.getId());
		
		//findBySubjcetAndContent - Repo Edit
//		questionEntity q = this.QueRepo.findBySubjectAndContent("sbb가 무엇인가요?","sbb가 알고싶어요?");
//		assertEquals(2, q.getId());
		
		//findBuySubjectLike - Repo Edit
//		List<questionEntity> qList = this.QueRepo.findBySubjectLike("sbb%");
//		questionEntity q = qList.get(0);
//		assertEquals("sbb가 알고싶어요?", q.getSubject());
		
		//질문 데이터 수정하는 테스트
//		Optional<questionEntity> oq = this.QueRepo.findById(1);
//		assertTrue(oq.isPresent());
//		questionEntity q = oq.get();
//		q.setSubject("수정된 제목");
//		this.QueRepo.save(q);
		
		//질문 데이터 삭제 테스트
//		assertEquals(2,this.QueRepo.count());
//		Optional<questionEntity>oq = this.QueRepo.findById(1);
//		assertTrue(oq.isPresent());
//		questionEntity q = oq.get();
//		this.QueRepo.delete(q);
//		assertEquals(1, this.QueRepo.count());
		
		//답변 데이터 저장하기
//		Optional<questionEntity>oq = this.QueRepo.findById(2);
//		assertTrue(oq.isPresent());
//		questionEntity q = oq.get();
//		
//		answerEntity a = new answerEntity();
//		a.setContent("네 자동으로 생성됩니다.");
//		a.setQuestion(q); 
		//속성에 대입
//		a.setCreateDate(LocalDateTime.now());
//		this.AnsRepo.save(a);
		
		//답변 데이터 조회하기
//		Optional<answerEntity>oa = this.AnsRepo.findById(1);
//		assertTrue(oa.isPresent());
//		answerEntity a = oa.get();
//		assertEquals(2,a.getQuestion().getId());
		
		//답변으로 질문 조회
		/*
		 * Optional<questionEntity>oq = this.QueRepo.findById(2);
		 * assertTrue(oq.isPresent()); questionEntity q = oq.get();
		 * 
		 * List<answerEntity>answerList = q.getAnswerList();
		 * 
		 * assertEquals(1, answerList.size()); assertEquals("네 자동으로 생성됩니다.",
		 * answerList.get(0).getContent());
		 */
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.QueSer.create(subject, content, null);
            System.out.print("success");
        }
	}
}


