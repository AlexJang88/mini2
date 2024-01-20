package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	//게시글등록
	public void register(BoardVO board);
	//게시글 하나 가져오기
	public BoardVO get(Long bno);
	//게시글 수정
	public boolean modify(BoardVO board);
	//게시글 삭제
	public boolean remove(Long bno);

	// public List<BoardVO> getList();
	//게시글 전체목록 조회
	public List<BoardVO> getList(Criteria cri);

	//게시글 전체갯수 조회
	public int getTotal(Criteria cri);
	//게시글(bno)에 해당하는 첨부파일 전체리스트
	public List<BoardAttachVO> getAttachList(Long bno);
	//게시글(bno)에 해당하는 첨부파일 삭제
	public void removeAttach(Long bno);

}
