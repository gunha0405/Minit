package kr.or.iei;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.text.model.dto.TextFeed;

@Controller
public class HomeController {
	@GetMapping(value="/")
	public String main() {
		return "index";
	}
}