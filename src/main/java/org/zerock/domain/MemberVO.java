package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	//유저아이디
	private String userid;
	//유저비밀번호
	private String userpw;
	//유저이름
	private String userName;
	//권한
	private boolean enabled;
	//가입일
	private Date regDate;
	//회원정보수정일
	private Date updateDate;
	//권한 리스트
	private List<AuthVO> authList;

}
