package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
		
		public int insert(ReplyVO vo);   //게시글 번호를 전달하여 신규댓글을 인서트하는 코드

	   public ReplyVO read(Long bno);   //게시글 번호를 댓글 번호에 넣은 후 전달하여 해당 번호의 댓글 내용가져오기

	   public int delete(Long bno);  //게시글 번호를 댓글 번호에 넣은 후 전달하여  댓글 삭제

	   public int update(ReplyVO reply);  //수정 내용을 전달하여 수정된 댓글 날짜와, 내용 업데이트

	   public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno); // 게시글 번호를전달하여 정렬 후 댓글 내용 가져오는 코드

	   public int getCountByBno(Long bno);   //게시글 번호를 전달하여 댓글이 몇개인지 나타내는 코드
}
