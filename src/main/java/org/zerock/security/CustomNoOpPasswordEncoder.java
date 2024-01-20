package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {
	//DB에 패스워드 인코딩 처리해서 넣음
	public String encode(CharSequence rawPassword) {

		log.warn("before encode :" + rawPassword);

		return rawPassword.toString();
	}
	//입력한 패스워드와 인코딩된 패스워드가 같은지 확인
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		log.warn("matches: " + rawPassword + ":" + encodedPassword);

		return rawPassword.toString().equals(encodedPassword);
	}

}
