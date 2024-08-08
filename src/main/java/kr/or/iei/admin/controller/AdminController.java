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
		System.out.println(list);
		model.addAttribute("list", list);
		return "admin/warningUser";
	}
	
	@GetMapping(value="/changeCount")
	public String changeCount(User u, Model model) {
		int result = userService.changeCount(u);
		if(result>0) {
			return "redirect:/admin/warningUser";
		}else {
			model.addAttribute("title", "경고주기 실패");
			model.addAttribute("msg", "개발자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/admin/warningUser");
			return "common/msg";
		}
	}
	
	
	
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
