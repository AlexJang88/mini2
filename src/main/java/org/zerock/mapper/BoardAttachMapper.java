package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//÷������ ���
	public void insert(BoardAttachVO vo);
	//÷������ �ϳ�(uuid) ����
	public void delete(String uuid);
	//�Խñ�(bno)�� �ش��ϴ� ÷������ ��ü ��ȸ
	public List<BoardAttachVO> findByBno(Long bno);
	//�Խñ�(bno)�� �ش��ϴ� ÷������ ��ü ����
	public void deleteAll(Long bno);
	//������¥ ������ü ��� ��������
	public List<BoardAttachVO> getOldFiles();

}