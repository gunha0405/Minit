package kr.or.iei.feed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.feed.model.service.FeedService;
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
		return "feed/list";
	}
	@GetMapping(value="/view")
	public String view() {
		return "feed/view";
	}
}
