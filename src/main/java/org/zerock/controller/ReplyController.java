package org.zerock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	private ReplyService service;
	//권한 여부 확인
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		
		log.info("ReplyVO: " + vo);
		//댓글 등록 (댓글갯수증가)
		int insertCount = service.register(vo);

		log.info("Reply INSERT COUNT: " + insertCount);
		//댓글이 등록되면  OK 상태코드와, "success" 메시지를 담은 ResponseEntity객체생성 아니면 Error 상태코드 담은 객체 생성
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//댓글(rno) 하나정보 가져오기
	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {

		log.info("get: " + rno);
		// 댓글(rno) 하나정보와 OK상태 코드를 담은 객체를 생성해서 리턴
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	//댓글 등록자만 권한이 주어짐 (권한체크)
	//댓글 수정
	@PreAuthorize("principal.username == #vo.replyer")
	@RequestMapping(method = { RequestMethod.PUT,
			RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {

		log.info("rno: " + rno);
		log.info("modify: " + vo);
		//댓글 작성 성공하면 OK 상태코드와, "success" 메시지를 담은 ResponseEntity객체생성해서 리턴 아니면 Error 상태코드 담은 객체 생성해서 리턴
		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// @DeleteMapping(value = "/{rno}", produces = { MediaType.TEXT_PLAIN_VALUE })
	// public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
	//
	// log.info("remove: " + rno);
	//
	// return service.remove(rno) == 1 ? new ResponseEntity<>("success",
	// HttpStatus.OK)
	// : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	//
	// }
	
	//댓글 작성자만 권한 가짐(권한 체크)
	//댓글 삭제
	@PreAuthorize("principal.username == #vo.replyer")
	@DeleteMapping("/{rno}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {

		log.info("remove: " + rno);

		log.info("replyer: " + vo.getReplyer());
		//댓글 삭제 성공하면 OK 상태코드와, "success" 메시지를 담은 ResponseEntity객체생성해서 리턴 아니면 Error 상태코드 담은 객체 생성해서 리턴
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// @GetMapping(value = "/pages/{bno}/{page}",
	// produces = {
	// MediaType.APPLICATION_XML_VALUE,
	// MediaType.APPLICATION_JSON_UTF8_VALUE })
	// public ResponseEntity<List<ReplyVO>> getList(
	// @PathVariable("page") int page,
	// @PathVariable("bno") Long bno) {
	//
	//
	// log.info("getList.................");
	// Criteria cri = new Criteria(page,10);
	// log.info(cri);
	//
	// return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	// }
	
	
	//게시글(bno)에 해당하는 댓글 전체목록 조회
	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno) {
		
		//댓글 10개마다 페이징처리하는 class 생성
		Criteria cri = new Criteria(page, 10);
		
		log.info("get Reply List bno: " + bno);

		log.info("cri:" + cri);
		//게시글(bno)에 해당하는 댓글전체목록 페이징처리해온 값과 ,OK 상태코드를 리턴
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}

}
