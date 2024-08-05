package kr.or.iei.feed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.iei.feed.model.service.FeedService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.util.FileUtils;

@Controller
@RequestMapping(value="/feed")
public class FeedController {
	@Autowired
	FeedService feedService = new FeedService();
	@Value("${file.root}")
	private String root;
	@Autowired
	FileUtils fileUtils = new FileUtils();
	
	@GetMapping(value="/list")
	public String list() {
		return "feed/list";   //작성자의 아이디나 넘버를 보낸다 
	}
	@GetMapping(value="/view")
	public String view() {
		return "feed/view";
	}
	@GetMapping(value="/userFollow")
	public void follow() {
		
	}
	
	@GetMapping(value="/userFollowCnacle")
	public void userFollowCnacle() {
		
	}
}
