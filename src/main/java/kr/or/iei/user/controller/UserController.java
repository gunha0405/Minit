package kr.or.iei.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login")
	public String login(User user, Model model, HttpSession session) {
		User getUser = userService.selectOneUser(user);
		System.out.println(getUser.getUserLevel());
		if(getUser == null) {
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/");
			return "common/msg";
		}else {
			if(getUser.getUserLevel() == 3) {
				model.addAttribute("title", "로그인 권한 없음");
				model.addAttribute("msg", "관리자에게 문의하세요");
				model.addAttribute("icon","warning");
				model.addAttribute("loc", "/");
				return "common/msg";
			}else {
				session.setAttribute("user", getUser);
				return "redirect:/";
			}
		}
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	
	
	
	
	
	
	
	
	/*
	@GetMapping(value="/login")
	public String login(User user, Model model, HttpSession session) {
		User getUser = userService.selectUser(user, null);
		return "redirect:/";
	}
	
	
	@GetMapping(value="/join")
	public String join(User user, Model model) {
		int result = userService.insertUser(user);
		return "redirect:/";
	}
	
	@GetMapping(value="/update")
	public String update(User user, @SessionAttribute User getUSer) {
		int result = userService.updateUser(user);
		return "redirect:/";
	}
	
	@GetMapping(value="/delete")
	public String deleteUser(@SessionAttribute User user, Model model) {
		int result = userService.deleteUser(user);
		if(result > 0) {
			model.addAttribute("title", "탈퇴완료");
			model.addAttribute("msg", "만나서 반가웠고 다시는 보지말자");
			model.addAttribute("icon", "success");			
			model.addAttribute("loc", "/user/logout");			
		}else {
			model.addAttribute("title", "탈퇴실패");
			model.addAttribute("msg", "처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
			model.addAttribute("icon", "error");			
			model.addAttribute("loc", "/user/mypage");			
		}
		return "common/msg";
	}
	
	@GetMapping(value="/mypage")
//	public String mypage(User user, Model model, HttpSession session) {
	public String mypage(User user, Model model) {
		
		return "/user/mypage";
	}
*/
}