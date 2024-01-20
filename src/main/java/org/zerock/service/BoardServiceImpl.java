package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Log4j
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	//setter메서드의 생성 시 메서드에 추가할 어노테이션을 지정함
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		
		log.info("register......" + board);
		//글작성 (board insert)
		mapper.insertSelectKey(board);
		
		
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		//파일 있으면 boardattach테이블에 저장
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	//게시글 하나 정보 가져오기
	@Override
	public BoardVO get(Long bno) {

		log.info("get......" + bno);

		return mapper.read(bno);

	}
	
	//게시글 수정
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		
		log.info("modify......" + board);
		
		//기존에 파일 db 삭제
		attachMapper.deleteAll(board.getBno());
		//DB업데이트가 되면 true,아니면 false
		boolean modifyResult = mapper.update(board) == 1;
		//등록된 파일이 있으면
		if (modifyResult && board.getAttachList() != null) {
			//attach Db에 insert
			board.getAttachList().forEach(attach -> {

				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}

	// @Override
	// public boolean modify(BoardVO board) {
	//
	// log.info("modify......" + board);
	//
	// return mapper.update(board) == 1;
	// }

	// @Override
	// public boolean remove(Long bno) {
	//
	// log.info("remove...." + bno);
	//
	// return mapper.delete(bno) == 1;
	// }
	
	
	//게시글 삭제
	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);
		//첨부파일 전체삭제
		attachMapper.deleteAll(bno);
		//게시글 삭제
		return mapper.delete(bno) == 1;
	}

	// @Override
	// public List<BoardVO> getList() {
	//
	// log.info("getList..........");
	//
	// return mapper.getList();
	// }
	
	
	//페이징처리(Criteria)해서 글전체목록(검색결과-Criteria) 가져오기
	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		log.info("get List with criteria: " + cri);
		
		return mapper.getListWithPaging(cri);
	}
	
	//검색결과(Criteria)총 갯수 가져오기
	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	
	//게시글에 등록된 첨부파일 목록 리스트 가져오기
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}
	
	//게시글에 등록된 첨부파일 전체 삭제
	@Override
	public void removeAttach(Long bno) {

		log.info("remove all attach files");

		attachMapper.deleteAll(bno);
	}

}
