package org.zerock.domain;


import lombok.Data;

@Data
public class AuthVO {
	
	//유저 아이디
  private String userid;
  //유저권한
  private String auth;
  
}
