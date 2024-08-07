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
import kr.or.iei.feed.model.dto.UserFeedNaviList;
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
		if (user == null) {
			model.addAttribute("title", "로그인을 해주세요");
			model.addAttribute("msg", "서비스 이용이 불가합니다");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/#");
			return "common/msg";
		} else {
			Feed feed = new Feed();
			User u = feedService.searchUser(user.getUserId());
			//페이지 구현 / 유저와 페이지에 들어가는 피드, 네비바
			//또다른 유저로 들어가게 되면은 그 유저의 번호 가지고 있게 하자. ??? 
			int reqPage = 1;
			//BoardListData list = boardService.selectAllBoard(reqPage);
			//model.addAttribute("list", list.getList());
			//model.addAttribute("pageNavi", list.getPageNavi());
			
			feed.setUser(u);
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
			String savepath = root+"/feed/";
            for(MultipartFile file : upfile) {
                //파일경로 복사, 중복된 이름 있으면 인덱스 작업
                String filepath = fileUtils.upload(savepath, file); // ice5_2.png
                FeedFile feedFile = new FeedFile();
                feedFile.setUserFeedFilpath(filepath);
                fileList.add(feedFile);
               }
        }
        // fileList 첨부파일갯수
        int result = feedService.insertfile(f,fileList);
        if(result >0) {
            model.addAttribute("title","작성성공!");
            model.addAttribute("msg","공지사항 작성에 성공했습니다.");
            model.addAttribute("icon","success");
            model.addAttribute("loc","/feed/list");
            return "common/msg";
        }
        return "redirect:/feed/list";
	}
}
