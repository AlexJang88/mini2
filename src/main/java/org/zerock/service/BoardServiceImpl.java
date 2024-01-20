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
	//setter�޼����� ���� �� �޼��忡 �߰��� ������̼��� ������
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		
		log.info("register......" + board);
		//���ۼ� (board insert)
		mapper.insertSelectKey(board);
		
		
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		//���� ������ boardattach���̺� ����
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	//�Խñ� �ϳ� ���� ��������
	@Override
	public BoardVO get(Long bno) {

		log.info("get......" + bno);

		return mapper.read(bno);

	}
	
	//�Խñ� ����
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		
		log.info("modify......" + board);
		
		//������ ���� db ����
		attachMapper.deleteAll(board.getBno());
		//DB������Ʈ�� �Ǹ� true,�ƴϸ� false
		boolean modifyResult = mapper.update(board) == 1;
		//��ϵ� ������ ������
		if (modifyResult && board.getAttachList() != null) {
			//attach Db�� insert
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
	
	
	//�Խñ� ����
	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);
		//÷������ ��ü����
		attachMapper.deleteAll(bno);
		//�Խñ� ����
		return mapper.delete(bno) == 1;
	}

	// @Override
	// public List<BoardVO> getList() {
	//
	// log.info("getList..........");
	//
	// return mapper.getList();
	// }
	
	
	//����¡ó��(Criteria)�ؼ� ����ü���(�˻����-Criteria) ��������
	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		log.info("get List with criteria: " + cri);
		
		return mapper.getListWithPaging(cri);
	}
	
	//�˻����(Criteria)�� ���� ��������
	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	
	//�Խñۿ� ��ϵ� ÷������ ��� ����Ʈ ��������
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}
	
	//�Խñۿ� ��ϵ� ÷������ ��ü ����
	@Override
	public void removeAttach(Long bno) {

		log.info("remove all attach files");

		attachMapper.deleteAll(bno);
	}

}
