package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	//�Խñ� ��ȣ
	private Long bno;
	//����
	private String title;
	//����
	private String content;
	//�ۼ���
	private String writer;
	//�Խñ� �����
	private Date regdate;
	//�Խñ� ������
	private Date updateDate;
	//��� ����
	private int replyCnt;
	//÷������ ���
	private List<BoardAttachVO> attachList;
}
