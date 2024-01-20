package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	//로그인시 권한이 안맞을때 accessError페이지로 갑니다.
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		
		log.info("access Denied : " + auth);
		//AccessError 페이지로 갈때 넘겨주는 메세지
		model.addAttribute("msg", "Access Denied");
	}
	
	//로그인
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		log.info("error: " + error);
		log.info("logout: " + logout);
		//로그인 정보가 일치하지않으면 error 메세지 전달
		if (error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		//로그아웃 하면 logout 메세지 전달
		if (logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
	//로그아웃처리(get방식)
	@GetMapping("/customLogout")
	public void logoutGET() {
		log.info("custom logout");
	}
	//로그아웃처리(POST방식)
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post custom logout");
	}

}
