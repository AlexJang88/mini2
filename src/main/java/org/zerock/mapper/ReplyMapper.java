package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
		
		public int insert(ReplyVO vo);   //�Խñ� ��ȣ�� �����Ͽ� �űԴ���� �μ�Ʈ�ϴ� �ڵ�

	   public ReplyVO read(Long bno);   //�Խñ� ��ȣ�� ��� ��ȣ�� ���� �� �����Ͽ� �ش� ��ȣ�� ��� ���밡������

	   public int delete(Long bno);  //�Խñ� ��ȣ�� ��� ��ȣ�� ���� �� �����Ͽ�  ��� ����

	   public int update(ReplyVO reply);  //���� ������ �����Ͽ� ������ ��� ��¥��, ���� ������Ʈ

	   public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno); // �Խñ� ��ȣ�������Ͽ� ���� �� ��� ���� �������� �ڵ�

	   public int getCountByBno(Long bno);   //�Խñ� ��ȣ�� �����Ͽ� ����� ����� ��Ÿ���� �ڵ�
}
