package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	//�����̸�
	private String fileName;
	//���ϰ��
	private String uploadPath;
	//���ϰ����̸�
	private String uuid;
	//�̹���Ÿ�� üũ
	private boolean image;

}
