package kr.or.iei.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.util.FileUtils;

@Controller
@RequestMapping(value="board")
public class BoardController<bestfeed> {
	@Autowired
	private BoardService boardService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	// 모든 텍스트 피드를 목록으로 표시
   
}


