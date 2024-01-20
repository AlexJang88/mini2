package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	//�Խñ۵��
	public void register(BoardVO board);
	//�Խñ� �ϳ� ��������
	public BoardVO get(Long bno);
	//�Խñ� ����
	public boolean modify(BoardVO board);
	//�Խñ� ����
	public boolean remove(Long bno);

	// public List<BoardVO> getList();
	//�Խñ� ��ü��� ��ȸ
	public List<BoardVO> getList(Criteria cri);

	//�Խñ� ��ü���� ��ȸ
	public int getTotal(Criteria cri);
	//�Խñ�(bno)�� �ش��ϴ� ÷������ ��ü����Ʈ
	public List<BoardAttachVO> getAttachList(Long bno);
	//�Խñ�(bno)�� �ش��ϴ� ÷������ ����
	public void removeAttach(Long bno);

}
