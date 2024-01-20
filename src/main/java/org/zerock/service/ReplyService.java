package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	
	   public int register(ReplyVO vo); //����� dto= vo �� �Ѱ� �������� /�űԴ���� �μ�Ʈ�ϴ� �ڵ�

	   public ReplyVO get(Long rno); //�Խñ� ��ȣ�� �����Ͽ� �ش� ��ȣ�� ��� ���밡������

	   public int modify(ReplyVO vo);  //����� dto= vo �� �Ѱ� ��������

	   public int remove(Long rno); // �Խñ�(rno)�� �ش��ϴ� ��� ��ü ����

	   public List<ReplyVO> getList(Criteria cri, Long bno); // �Խñ� �� ��� ������ �ҷ����� ����
	   
	   public ReplyPageDTO getListPage(Criteria cri, Long bno); //�Խñ�(bno)�� �ش��ϴ� ��� ����¡ó���ؼ� ��ü��� ��ȸ
	   

}
