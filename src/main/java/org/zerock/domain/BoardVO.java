package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	//게시글 번호
	private Long bno;
	//제목
	private String title;
	//내용
	private String content;
	//작성자
	private String writer;
	//게시글 등록일
	private Date regdate;
	//게시글 수정일
	private Date updateDate;
	//댓글 갯수
	private int replyCnt;
	//첨부파일 목록
	private List<BoardAttachVO> attachList;
}
