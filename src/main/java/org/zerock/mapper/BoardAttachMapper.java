package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//첨부파일 등록
	public void insert(BoardAttachVO vo);
	//첨부파일 하나(uuid) 삭제
	public void delete(String uuid);
	//게시글(bno)에 해당하는 첨부파일 전체 조회
	public List<BoardAttachVO> findByBno(Long bno);
	//게시글(bno)에 해당하는 첨부파일 전체 삭제
	public void deleteAll(Long bno);
	//어제날짜 파일전체 목록 가져오기
	public List<BoardAttachVO> getOldFiles();

}