package kr.or.iei;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.util.FileUtils;


@Controller

public class HomeController {

    @Autowired
    private BoardService boardService;

    @Value("${file.root}")
    private String root;

    @Autowired
    private FileUtils fileUtils;

    @GetMapping(value = "/")
    public String main() {
        return "index";
    }

    @GetMapping("/index")
    @ResponseBody
    public List<Board> getAllBoards() {
        return boardService.getAllBoards(); // 단순히 게시판 리스트를 반환
    }

    
}