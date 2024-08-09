package kr.or.iei.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/warningMain")
	public String warningMain() {
		return "admin/warningMain";
	}
	
	@GetMapping(value="/warningUser")
	public String warningUser(Model model) {
		List list = userService.selectAllUser();
		//System.out.println(list);
		model.addAttribute("list", list);
		return "admin/warningUser";
	}
	
	@GetMapping(value="/changeCount")
	public String changeCount(User u, Model model) {
		int result = userService.changeCount(u);
		if(result == 2){
			model.addAttribute("title", "경고 성공");
			model.addAttribute("msg", "누적 경고가  5회를 달성하여 해당 회원이 정지되었습니다. ");
			model.addAttribute("icon","warning");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}else if(result == 1){
			model.addAttribute("title", "경고 성공");
			model.addAttribute("msg", "경고 횟수가 누적되었습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}else {
			model.addAttribute("title", "경고 실패");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}
	}
	
	@GetMapping(value="/checkedChangeCount")
	public String checkedChangeCount(String no, String count, Model model) {
		//System.out.println(no);
		//System.out.println(count);
		int result = userService.checkedChangeCount(no, count);
		if(result == 2){
			model.addAttribute("title", "경고 성공");
			model.addAttribute("msg", "누적 경고가  5회를 달성하여 정지된 회원이 있습니다. ");
			model.addAttribute("icon","warning");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}else if(result == 1){
			model.addAttribute("title", "경고 성공");
			model.addAttribute("msg", "경고 횟수가 누적되었습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}else {
			model.addAttribute("title", "경고 실패");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}
	}
	/*
	@GetMapping(value="/checkedChangeCount")
	public String checkedChangeCount(String no, String count, Model model) {
		//System.out.println(no);
		//System.out.println(count);
		boolean result = userService.checkedChangeCount(no, count);
		if(result) {
			return "redirect:/admin/warningUser";			
		}else {
			model.addAttribute("title", "경고 실패");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}
	}
	*/
	
	
	@GetMapping(value="/warningText")
	public String warningText(Model model) {
		List list = userService.selectAllUser();
		System.out.println(list);
		model.addAttribute("list", list);
		return "admin/warningText";
	}
	
	
	
	@GetMapping(value="/warningPhoto")
	public String warningPhoto(Model model) {
		List list = userService.selectAllUser();
		System.out.println(list);
		model.addAttribute("list", list);
		return "admin/warningPhoto";
	}
}
