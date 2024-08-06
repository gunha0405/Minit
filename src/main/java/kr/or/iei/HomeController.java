package kr.or.iei;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/api/boards")
    @ResponseBody
    public List<Board> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        for (int i = 0; i < boards.size(); i++) {
            Board b = boards.get(i);
            b.setPhotoFeedImg("/images/" + b.getPhotoFeedImg());
        }
        
        return boards;
    }
}