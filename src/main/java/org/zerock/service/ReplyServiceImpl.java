package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Service
@Log4j
@Slf4j
public class ReplyServiceImpl implements ReplyService {

	//답글 매퍼
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	//게시글 매퍼
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	// @Override
	// public int register(ReplyVO vo) {
	//
	// log.info("register......" + vo);
	//
	// return mapper.insert(vo);
	//
	// }
	
	//댓글 등록
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		log.info("register......" + vo);
		//댓글갯수 1증가
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		//댓글 등록
		return mapper.insert(vo);

	}
	
	//게시글 하나(rno) 에 대한 댓글정보 가져오기
	@Override
	public ReplyVO get(Long rno) {
		
		log.info("get......" + rno);
		
		return mapper.read(rno);

	}
	//댓글 수정
	@Override
	public int modify(ReplyVO vo) {

		log.info("modify......" + vo);

		return mapper.update(vo);

	}

	// @Override
	// public int remove(Long rno) {
	//
	// log.info("remove...." + rno);
	//
	// return mapper.delete(rno);
	//
	// }
	
	//댓글 하나 삭제
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info("remove...." + rno);
		//댓글 하나정보 가져오기 
		ReplyVO vo = mapper.read(rno);
		//댓글갯수 1감소
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		//댓글 삭제
		return mapper.delete(rno);

	}
	
	
	//게시글하나(bno)에 대한 댓글 전체 정보 페이징 처리해서 가져오기
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		log.info("get Reply List of a Board " + bno);

		return mapper.getListWithPaging(cri, bno);

	}
	
	//댓글 페이징처리+댓글목록
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
					             //댓글 전체갯수              //댓글 페이징처리+댓글목록
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}

}
