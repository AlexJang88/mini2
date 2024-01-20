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
	//�α��ν� ������ �ȸ����� accessError�������� ���ϴ�.
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		
		log.info("access Denied : " + auth);
		//AccessError �������� ���� �Ѱ��ִ� �޼���
		model.addAttribute("msg", "Access Denied");
	}
	
	//�α���
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		log.info("error: " + error);
		log.info("logout: " + logout);
		//�α��� ������ ��ġ���������� error �޼��� ����
		if (error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		//�α׾ƿ� �ϸ� logout �޼��� ����
		if (logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
	//�α׾ƿ�ó��(get���)
	@GetMapping("/customLogout")
	public void logoutGET() {
		log.info("custom logout");
	}
	//�α׾ƿ�ó��(POST���)
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post custom logout");
	}

}
