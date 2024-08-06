package kr.or.iei.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.util.FileUtils;

@Controller
@RequestMapping(value="/")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	// 모든 텍스트 피드를 목록으로 표시
    @GetMapping("/index")
    public String textBoard(Model model) {
        List<TextFeed> textlist = boardService.AllTextFeeds();
        model.addAttribute("textlist", textlist);
        return "index"; // index.html 파일을 반환
    }
}


