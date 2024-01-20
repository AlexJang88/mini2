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

	//��� ����
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	//�Խñ� ����
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
	
	//��� ���
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		log.info("register......" + vo);
		//��۰��� 1����
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		//��� ���
		return mapper.insert(vo);

	}
	
	//�Խñ� �ϳ�(rno) �� ���� ������� ��������
	@Override
	public ReplyVO get(Long rno) {
		
		log.info("get......" + rno);
		
		return mapper.read(rno);

	}
	//��� ����
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
	
	//��� �ϳ� ����
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info("remove...." + rno);
		//��� �ϳ����� �������� 
		ReplyVO vo = mapper.read(rno);
		//��۰��� 1����
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		//��� ����
		return mapper.delete(rno);

	}
	
	
	//�Խñ��ϳ�(bno)�� ���� ��� ��ü ���� ����¡ ó���ؼ� ��������
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		log.info("get Reply List of a Board " + bno);

		return mapper.getListWithPaging(cri, bno);

	}
	
	//��� ����¡ó��+��۸��
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
					             //��� ��ü����              //��� ����¡ó��+��۸��
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}

}
