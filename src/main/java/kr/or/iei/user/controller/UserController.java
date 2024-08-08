package kr.or.iei.user.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
//import kr.or.iei.util.EmailSender;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	//로그인
	@PostMapping(value="/login")
	public String login(User u, Model model, HttpSession session) {
		User user = userService.selectOneUser(u);
		System.out.println(user);
		if(user == null) {
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/");
			return "common/msg";
		}else {
			if(user.getUserLevel() == 3) {
				model.addAttribute("title", "로그인 권한 없음");
				model.addAttribute("msg", "정지된 회원입니다. 관리자에게 문의하세요.");
				model.addAttribute("icon","warning");
				model.addAttribute("loc", "/");
				return "common/msg";
			}else {
				session.setAttribute("user", user);
				return "redirect:/";
			}
		}
	}
	
	//로그아웃
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	//마이페이지
	@GetMapping(value="/mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	//마이페이지 회원정보 수정
	@PostMapping(value="/update")
	public String update(User u, @SessionAttribute User user) {
		int result = userService.updateUser(u);
		if (result>0) {
			user.setUserNick(u.getUserNick());
			user.setUserInfo(u.getUserInfo());
			user.setUserPw(u.getUserPw());
			return "redirect:/user/mypage";
		}else {
			return "redirect:/";
		}
	}
	
	//사용자이름 중복체크
	@ResponseBody
	@GetMapping(value="/ajaxCheckNick")
	public int ajaxCheckNick(String userNick) {
		User user = userService.selectUserNick(userNick);
		if(user == null) {
			return 0;
		}else {
			return 1;
		}
	}
	
	//아이디중복체크
	@ResponseBody
	@GetMapping(value="/ajaxCheckId")
	public int ajaxCheckId(String userId) {
		User user = userService.selectUserId(userId);
		if(user == null) {
			return 0;
		}else {
			return 1;
		}
	}
	
	//회원가입
	@PostMapping(value = "/join")
	public String join(User u, Model model) {
		int result = userService.insertUser(u);
		if(result > 0) {
			model.addAttribute("title", "회원가입 성공");
			model.addAttribute("msg", "MINIT의 회원이 되셨습니다!");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/");
			return "common/msg";
		}else {
			return "redirect:/";			
		}
	}
	
	//회원탈퇴
	@GetMapping(value="/delete")
	public String deleteUser(@SessionAttribute User user, Model model) {
		int result = userService.deleteUser(user);
		if(result > 0) {
			model.addAttribute("title", "탈퇴완료");
			model.addAttribute("msg", "그동안 MINIT과 함께 해주셔서 감사했습니다.");
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
	
	//회원가입 시 email인증 관련
	@Autowired
	private EmailSender emailSender;
	
	@ResponseBody
	@PostMapping(value="/sendCode")
	public String sendCode(String receiver) {
		System.out.println(receiver);
		User user = userService.selectUserEmail(receiver);
		if(user == null && !receiver.equals("")) {
			//인증메일 제목
			String emailTitle = "MINIT 회원가입 이메일 인증 메일";
			//인증메일 인증코드
			Random r = new Random();
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<6; i++) {
				int flag = r.nextInt(3);
				if(flag == 0) { //숫자사용
					int randomCode = r.nextInt(10);
					sb.append(randomCode);
				}else if(flag == 1) {//대문자사용
					char randomCode = (char)(r.nextInt(26)+65);
					sb.append(randomCode);
				}else if(flag == 2) {//소문자사용
					char randomCode = (char)(r.nextInt(26)+97);
					sb.append(randomCode);
				}
			}
			String emailContent = "<h2>순간을 기록하다, MINIT 입니다.</h2>"
					+"<h3>귀하의 인증번호는 [ <span style='color:red;'>"
					+sb.toString()
					+"</span> ] 입니다.</h3>"
					+"<h3>대소문자를 꼭 구별하여 인증해주세요!</h3>";
			emailSender.sendMail(emailTitle, receiver, emailContent);
			return sb.toString();
		}else {
			return null;
		}
	}
	
	@GetMapping(value="/findaccount")
	public String findaccount() {
		return "user/findaccount";
	}
	
	//아이디 찾기
	@PostMapping(value="/searchId")
	public String searchId(User u, Model model) {		
		System.out.println(u);
		User user = userService.searchUserId(u);
		System.out.println(user);
		if(user == null) {
			model.addAttribute("title", "정보 조회 실패");
			model.addAttribute("msg", "해당 정보로 가입된 회원을 조회할 수 없습니다.");
			model.addAttribute("icon","error");
		}else {
			model.addAttribute("title", "아이디 조회 성공");
			model.addAttribute("msg", "귀하의 아이디는 ["+user.getUserId()+"]입니다.");
			model.addAttribute("icon","success");
		}
		model.addAttribute("loc", "/user/findaccount");
		return "common/msg";
	}
	
	//비밀번호 찾기-회원찾기
	@ResponseBody
	@GetMapping(value="/ajaxFindUser")
	public int ajaxFindUser(String userId, String userEmail) {
		User user = userService.ajaxFindUser(userId, userEmail);
		if(user != null) {
			return 1;
		}else {
			return 0;
		}
	}
	
	@PostMapping(value="/updatePw")
	public String updatePw(User u, Model model) {
	System.out.println(u);
		int result = userService.updatePw(u);
		if(result>0) {
			model.addAttribute("title", "비밀번호 변경 성공");
			model.addAttribute("msg", "변경된 비밀번호로 로그인 해주세요!");
			model.addAttribute("icon","success");
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "비밀번호 변경 실패");
			model.addAttribute("msg", "처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc", "/user/findaccount");
		}
		return "common/msg";
	}
	
}