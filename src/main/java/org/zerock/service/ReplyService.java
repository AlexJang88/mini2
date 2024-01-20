package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	
	   public int register(ReplyVO vo); //댓글의 dto= vo 를 넘겨 정보전달 /신규댓글을 인서트하는 코드

	   public ReplyVO get(Long rno); //게시글 번호를 전달하여 해당 번호의 댓글 내용가져오기

	   public int modify(ReplyVO vo);  //댓글의 dto= vo 를 넘겨 정보전달

	   public int remove(Long rno); // 게시글(rno)에 해당하는 댓글 전체 삭제

	   public List<ReplyVO> getList(Criteria cri, Long bno); // 게시글 속 댓글 내용을 불러오기 위해
	   
	   public ReplyPageDTO getListPage(Criteria cri, Long bno); //게시글(bno)에 해당하는 댓글 페이징처리해서 전체목록 조회
	   

}
