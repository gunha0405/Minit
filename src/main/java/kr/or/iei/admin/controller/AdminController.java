package kr.or.iei.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.service.FeedService;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.service.PhotoService;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.service.TextService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TextService textService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private FeedService feedService;
	
	@GetMapping(value="/warningMain")
	public String warningMain() {
		return "admin/warningMain";
	}
	
	@GetMapping(value="/allUser")
	public String warningUser(Model model) {
		List list = userService.selectAllUser();
		model.addAttribute("list", list);
		return "admin/allUser";
	}
	
	@GetMapping(value="/textChangeCount")
	public String textChangeCount(int userNo, Model model, int textFeedNo) {
		User u = userService.selectOneUser(userNo);
		List<TextFeed> reportList = textService.selectReportFeed();
		if(u != null) {
			int result = userService.textChangeCount(u, textFeedNo);
			model.addAttribute("reportList", reportList);
			if(result == 4){
				model.addAttribute("title", "회원정지 및 글삭제 성공");
				model.addAttribute("msg", "누적 경고가  5회를 달성하여 해당 회원이 정지되었습니다.");
				model.addAttribute("icon","info");
				model.addAttribute("loc", "/text/selectReportFeed");
				return "common/msg";
			}else if(result == 2){
				model.addAttribute("title", "회원경고 및 글삭제 성공");
				model.addAttribute("msg", "회원 경고 조치 및 해당 게시글 삭제에 성공하였습니다.");
				model.addAttribute("icon","success");
				model.addAttribute("loc", "/text/selectReportFeed");
				return "common/msg";
			}else if(result == 1){
				model.addAttribute("title", "경고 또는 글 삭제 실패");
				model.addAttribute("msg", "경고 또는 글 삭제에 실패했습니다. 개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/text/selectReportFeed");
				return "common/msg";
			}else {
				model.addAttribute("title", "경고 및 글 삭제 실패");
				model.addAttribute("msg", "개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/text/selectReportFeed"); //보내줄 컨트롤러
				return "common/msg";
			}
		}else {
			model.addAttribute("title", "error");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/text/selectReportFeed");
			return "common/msg";
		}
	}
	
	
	
	@GetMapping(value="/photoChangeCount")
	public String photoChangeCount(int userNo, Model model, int photoFeedNo) {
		User u = userService.selectOneUser(userNo);
		List<Photo> reportList = photoService.selectReportFeed();
		if(u != null) {
			int result = userService.photoChangeCount(u, photoFeedNo);
			model.addAttribute("reportList", reportList);
			if(result == 4){
				model.addAttribute("title", "회원정지 및 글삭제 성공");
				model.addAttribute("msg", "누적 경고가  5회를 달성하여 해당 회원이 정지되었습니다.");
				model.addAttribute("icon","info");
				model.addAttribute("loc", "/photo/selectReportFeed");
				return "common/msg";
			}else if(result == 2){
				model.addAttribute("title", "회원경고 및 글삭제 성공");
				model.addAttribute("msg", "회원 경고 조치 및 해당 게시글 삭제에 성공하였습니다.");
				model.addAttribute("icon","success");
				model.addAttribute("loc", "/photo/selectReportFeed");
				return "common/msg";
			}else if(result == 1){
				model.addAttribute("title", "경고 또는 글 삭제 실패");
				model.addAttribute("msg", "경고 또는 글 삭제에 실패했습니다. 개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/photo/selectReportFeed");
				return "common/msg";
			}else {
				model.addAttribute("title", "경고 및 글 삭제 실패");
				model.addAttribute("msg", "개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/photo/selectReportFeed"); //보내줄 컨트롤러
				return "common/msg";
			}
		}else {
			model.addAttribute("title", "error");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/photo/selectReportFeed");
			return "common/msg";
		}
	}
	
	
	@GetMapping(value="/feedChangeCount")
	public String feedChangeCount(int userNo, Model model, int userFeedNo) {
		User u = userService.selectOneUser(userNo);
		List<Feed> reportList = feedService.selectReportFeed();
		if(u != null) {
			int result = userService.feedChangeCount(u, userFeedNo);
			model.addAttribute("reportList", reportList);
			if(result == 4){
				model.addAttribute("title", "회원정지 및 글삭제 성공");
				model.addAttribute("msg", "누적 경고가  5회를 달성하여 해당 회원이 정지되었습니다.");
				model.addAttribute("icon","info");
				model.addAttribute("loc", "/feed/selectReportFeed");
				return "common/msg";
			}else if(result == 2){
				model.addAttribute("title", "회원경고 및 글삭제 성공");
				model.addAttribute("msg", "회원 경고 조치 및 해당 게시글 삭제에 성공하였습니다.");
				model.addAttribute("icon","success");
				model.addAttribute("loc", "/feed/selectReportFeed");
				return "common/msg";
			}else if(result == 1){
				model.addAttribute("title", "경고 또는 글 삭제 실패");
				model.addAttribute("msg", "경고 또는 글 삭제에 실패했습니다. 개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/feed/selectReportFeed");
				return "common/msg";
			}else {
				model.addAttribute("title", "경고 및 글 삭제 실패");
				model.addAttribute("msg", "개발자에게 문의하세요.");
				model.addAttribute("icon","error");
				model.addAttribute("loc", "/feed/selectReportFeed"); //보내줄 컨트롤러
				return "common/msg";
			}
		}else {
			model.addAttribute("title", "error");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/feed/selectReportFeed");
			return "common/msg";
		}
	}
	
	
	@GetMapping(value="/changeLevel")
	public String changeCount(User u, Model model) {
		int result = userService.changeLevel(u);
		if(result>0) {
			return "redirect:/admin/allUser";
		}else {
			model.addAttribute("title", "등급 변경 실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "admin/allMember");
			return "common/msg";
		}
	}
	
	@GetMapping(value="/checkedChangeLevel")
	public String checkedChangeCount(String no, String level, Model model) {
		boolean result = userService.checkedChangeLevel(no,level);
		if(result) {
			return "redirect:/admin/allUser";
		}else {
			model.addAttribute("title", "등급 변경 실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "admin/allMember");
			return "common/msg";
		}
	}
	
	@GetMapping(value="/warningPhoto")
	public String warningPhoto(Model model) {
		List list = userService.selectAllUser();
		System.out.println(list);
		model.addAttribute("list", list);
		return "admin/warningPhoto";
	}
}
