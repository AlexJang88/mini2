package org.zerock.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	//로그인 성공 했을때 권한에 따른 첫페이지 주소 처리
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		log.warn("Login Success");
		//Login한 회원에 대한 권한 List에 담기
		List<String> roleNames = new ArrayList<>();

		auth.getAuthorities().forEach(authority -> {

			roleNames.add(authority.getAuthority());

		});

		log.warn("ROLE NAMES: " + roleNames);
		
		//admin 권한일 경우 /board/list로 보냄
		if (roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/board/list");
			return;
		}
		
		//member 권한일 경우 /board/list로 보냄
		if (roleNames.contains("ROLE_MEMBER")) {

			response.sendRedirect("/board/list");
			return;
		}

		response.sendRedirect("/board/list");
	}
}


