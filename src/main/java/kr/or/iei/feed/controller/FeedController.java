package kr.or.iei.feed.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedList;
import kr.or.iei.feed.model.service.FeedService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.util.FileUtils;

@Controller
@RequestMapping(value = "/feed")
public class FeedController {
	@Autowired
	FeedService feedService = new FeedService();
	@Value("${file.root}")
	private String root;
	@Autowired
	FileUtils fileUtils = new FileUtils();

//	@GetMapping(value="/list")
//	public String list() {
//		return "feed/list";   //작성자의 아이디나 넘버를 보낸다 
//	}

	@GetMapping(value = "/view")
	public String view() {
		return "feed/view";
	}

	@GetMapping(value = "/userFollow") // 피드.리스트 완성후 작업예정
	public void follow() {

	}

	@GetMapping(value = "/userFollowCnacle") // 피드.리스트완성후 작업예정
	public void userFollowCnacle() {

	}

	@GetMapping(value = "/writeForm")
	public String wirteForm() {
		return "feed/writeForm";
	}

	@GetMapping(value = "/userStorage")
	public String userStorage() {
		return "/#";
	}

	@GetMapping(value = "/myPage")
	public String myPage(HttpSession session, Model model) {
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		if (user == null) {
			model.addAttribute("title", "로그인을 해주세요");
			model.addAttribute("msg", "서비스 이용이 불가합니다");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/#");
			return "common/msg";
		} else {
			User u = feedService.searchUser(user.getUserId());
			model.addAttribute("user", u);
			return "/feed/list";
		}
	}
	
	@PostMapping(value ="/feedWrite")
	public String feedWirte(Feed f, MultipartFile[] upfile, Model model) {
		List<FeedFile> fileList = new ArrayList<FeedFile>();
		if(upfile[0].isEmpty()) {
			model.addAttribute("title", "사진첨부 필요");
			model.addAttribute("msg", "사진을 첨부해야 서비스 이용이 가능합니다.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/feed/writeForm");
			return "common/msg";
		}else {
			
			return "";
		}
	}
}
