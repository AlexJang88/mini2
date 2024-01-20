package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
import org.zerock.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	//MemberMapper DI 받음
	@Setter(onMethod_ = { @Autowired })
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

		log.warn("Load User By UserName : " + userid);

		// userName means userid
		//로그인사용자 정보가져옴
		MemberVO vo = memberMapper.read(userid);

		log.warn("queried by member mapper: " + vo);
		//사용자 정보가 있으면 CustomUser(vo) 생성자 생성
		return vo == null ? null : new CustomUser(vo);
	} 

}
