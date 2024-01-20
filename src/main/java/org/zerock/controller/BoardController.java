package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")  // isAuthenticated() �α��μ������� Ȯ�� , �α��� ������ ��밡����
	public void register() {

	}

	// @GetMapping("/list")
	// public void list(Model model) {
	//
	// log.info("list");
	// model.addAttribute("list", service.getList());
	//
	// }

	// @GetMapping("/list")
	// public void list(Criteria cri, Model model) {
	//
	// log.info("list: " + cri);
	// model.addAttribute("list", service.getList(cri));
	//
	// }
	
	//�Խñ� ��ü ���(�˻���� ���� - Critera) �������� , �ѰԽù� ����(�˻���� ����-Critera)
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {

		log.info("list: " + cri);
		//�Խñ� ��ü ��ȸ(�˻���� ����)
		model.addAttribute("list", service.getList(cri));
		// model.addAttribute("pageMaker", new PageDTO(cri, 123));
		//�Ѱ��ù�����(�˻���� ����)
		int total = service.getTotal(cri);

		log.info("total: " + total);
		//����¡ ó��
		model.addAttribute("pageMaker", new PageDTO(cri, total));

	}

	// @PostMapping("/register")
	// public String register(BoardVO board, RedirectAttributes rttr) {
	//
	// log.info("register: " + board);
	//
	// service.register(board);
	//
	// rttr.addFlashAttribute("result", board.getBno());
	//
	// return "redirect:/board/list";
	// }

	//�Խñ� ���
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("==========================");

		log.info("register: " + board);
		
		if (board.getAttachList() != null) {
			//���� ������ �α�����
			board.getAttachList().forEach(attach -> log.info(attach));

		}

		log.info("==========================");
			//�Խñ� ���
		service.register(board);
			//�Խñ� ��ȣ board/list�� redirect�� ��ȸ���ڷ�� ����
		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}

	// @GetMapping({ "/get", "/modify" })
	// public void get(@RequestParam("bno") Long bno, Model model) {
	//
	// log.info("/get or modify ");
	// model.addAttribute("board", service.get(bno));
	// }
	
	//�Խñ� �ϳ� �������� /get , /modify �ΰ� �����س���
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

		log.info("/get or modify");
		//�Խñ� �ϳ� ������ ������
		model.addAttribute("board", service.get(bno));
	}

	// @PostMapping("/modify")
	// public String modify(BoardVO board, RedirectAttributes rttr) {
	// log.info("modify:" + board);
	//
	// if (service.modify(board)) {
	// rttr.addFlashAttribute("result", "success");
	// }
	// return "redirect:/board/list";
	// }

//	@PostMapping("/modify")
//	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
//		log.info("modify:" + board);
//
//		if (service.modify(board)) {
//			rttr.addFlashAttribute("result", "success");
//		}
//
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
//
//		return "redirect:/board/list";
//	}

	//�ۼ����϶��� ������ ������
	@PreAuthorize("principal.username == #board.writer")
	//�Խñ� ����
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + board);
		//�Խñ� ����
		if (service.modify(board)) {
			//�Խñ� ���������ϸ� result�� ����
			rttr.addFlashAttribute("result", "success");
		}
		//list�� ������ �Խñ۰˻����+����¡ó���ؼ� ����
		return "redirect:/board/list" + cri.getListLink();
	}
	
	

	// @PostMapping("/remove")
	// public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr)
	// {
	//
	// log.info("remove..." + bno);
	// if (service.remove(bno)) {
	// rttr.addFlashAttribute("result", "success");
	// }
	// return "redirect:/board/list";
	// }

	// @PostMapping("/remove")
	// public String remove(@RequestParam("bno") Long bno, Criteria cri,
	// RedirectAttributes rttr) {
	//
	// log.info("remove..." + bno);
	// if (service.remove(bno)) {
	// rttr.addFlashAttribute("result", "success");
	// }
	// rttr.addAttribute("pageNum", cri.getPageNum());
	// rttr.addAttribute("amount", cri.getAmount());
	// rttr.addAttribute("type", cri.getType());
	// rttr.addAttribute("keyword", cri.getKeyword());
	//
	// return "redirect:/board/list";
	// }
	
	//�ۼ��� �϶��� ��������
	@PreAuthorize("principal.username == #writer")
	//�Խñ� ����
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr, String writer) {

		log.info("remove..." + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		//�Խñۻ���
		if (service.remove(bno)) {

			// delete Attach Files
			deleteFiles(attachList);
			//�Խñ� �����ϸ� result ����
			rttr.addFlashAttribute("result", "success");
		}
		//list�� ������ �Խñ۰˻����+����¡ó���ؼ� ����
		return "redirect:/board/list" + cri.getListLink();
	}
	//���� ����
	private void deleteFiles(List<BoardAttachVO> attachList) {

		if (attachList == null || attachList.size() == 0) {
			return;
		}

		log.info("delete attach files...................");
		log.info(attachList);
		//������ �����ϸ�
		attachList.forEach(attach -> {
			try {
				//÷������ ����(���� ����)
				Path file = Paths.get(
						"C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

				Files.deleteIfExists(file);
				//�������� ����
				if (Files.probeContentType(file).startsWith("image")) {

					Path thumbNail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_"
							+ attach.getFileName());

					Files.delete(thumbNail);
				}

			} catch (Exception e) {
				log.error("delete file error" + e.getMessage());
			} // end catch
		});// end foreachd
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {

		log.info("getAttachList " + bno);

		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);

	}

}
