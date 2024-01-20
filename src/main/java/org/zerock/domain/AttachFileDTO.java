package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	//파일이름
	private String fileName;
	//파일경로
	private String uploadPath;
	//파일고유이름
	private String uuid;
	//이미지타입 체크
	private boolean image;

}
