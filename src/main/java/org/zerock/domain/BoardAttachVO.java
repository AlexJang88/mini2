package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	
	//파일 고유이름
  private String uuid;
  //파일 경로
  private String uploadPath;
  //파일이름
  private String fileName;
  //파일타입
  private boolean fileType;
  //게시글 번호
  private Long bno;
  
}
