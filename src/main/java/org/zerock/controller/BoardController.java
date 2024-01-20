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
	@PreAuthorize("isAuthenticated()")  // isAuthenticated() 로그인성공여부 확인 , 로그인 성공후 사용가능함
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
	
	//게시글 전체 목록(검색결과 포함 - Critera) 가져오기 , 총게시물 갯수(검색결과 포함-Critera)
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {

		log.info("list: " + cri);
		//게시글 전체 조회(검색결과 포함)
		model.addAttribute("list", service.getList(cri));
		// model.addAttribute("pageMaker", new PageDTO(cri, 123));
		//총개시물갯수(검색결과 포함)
		int total = service.getTotal(cri);

		log.info("total: " + total);
		//페이징 처리
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

	//게시글 등록
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("==========================");

		log.info("register: " + board);
		
		if (board.getAttachList() != null) {
			//파일 있으면 로그찍음
			board.getAttachList().forEach(attach -> log.info(attach));

		}

		log.info("==========================");
			//게시글 등록
		service.register(board);
			//게시글 번호 board/list로 redirect로 일회성자료로 보냄
		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}

	// @GetMapping({ "/get", "/modify" })
	// public void get(@RequestParam("bno") Long bno, Model model) {
	//
	// log.info("/get or modify ");
	// model.addAttribute("board", service.get(bno));
	// }
	
	//게시글 하나 가져오기 /get , /modify 두개 매핑해놓음
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

		log.info("/get or modify");
		//게시글 하나 정보를 가져옴
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

	//작성자일때만 수정이 가능함
	@PreAuthorize("principal.username == #board.writer")
	//게시글 수정
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + board);
		//게시글 수정
		if (service.modify(board)) {
			//게시글 수정성공하면 result를 보냄
			rttr.addFlashAttribute("result", "success");
		}
		//list로 수정전 게시글검색결과+페이징처리해서 보냄
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
	
	//작성자 일때만 삭제가능
	@PreAuthorize("principal.username == #writer")
	//게시글 삭제
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr, String writer) {

		log.info("remove..." + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		//게시글삭제
		if (service.remove(bno)) {

			// delete Attach Files
			deleteFiles(attachList);
			//게시글 삭제하면 result 보냄
			rttr.addFlashAttribute("result", "success");
		}
		//list로 삭제전 게시글검색결과+페이징처리해서 보냄
		return "redirect:/board/list" + cri.getListLink();
	}
	//파일 삭제
	private void deleteFiles(List<BoardAttachVO> attachList) {

		if (attachList == null || attachList.size() == 0) {
			return;
		}

		log.info("delete attach files...................");
		log.info(attachList);
		//파일이 존재하면
		attachList.forEach(attach -> {
			try {
				//첨부파일 삭제(사진 제외)
				Path file = Paths.get(
						"C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

				Files.deleteIfExists(file);
				//사진파일 삭제
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
