package kr.or.iei.feed.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.feed.model.dto.feedListData;
import kr.or.iei.feed.model.service.FeedService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;
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

	@GetMapping(value = "/list")
	public String list() {
		return "feed/list"; // 작성자의 아이디나 넘버를 보낸다
	}
	
	@GetMapping(value = "/writeForm")
	public String wirteForm() {
		return "feed/writeForm";
	}

	// feed-view
	@GetMapping(value = "/view")
	public String view(int userFeedNo, Model model) {
		Feed feed = feedService.selectUserOneFeed(userFeedNo); // 해당 글 유저의 정보와 사진 파일리스트
		model.addAttribute("feed", feed);
		model.addAttribute("list", feed.getFeedList());
		return "feed/view";
	}

	@GetMapping(value = "/userFollow") // 피드.리스트 완성후 작업예정
	public void follow() {

	}

	@GetMapping(value = "/userFollowCnacle") // 피드.리스트완성후 작업예정
	public void userFollowCnacle() {

	}

	@GetMapping(value = "/userStorage")
	public String userStorage() {
		return "/#";
	}

	// 유저 아디디도 받아야 할것 같음 검색해야할것 같음
	@GetMapping(value = "/myPage")
	public String myPage(Integer reqPage, String userFeedWriter, Model model) {
		reqPage =  (reqPage != null) ? reqPage : 1;
		//User user = (User) session.getAttribute("user");
		//String userId = feedId.getUserFeedWriter();
		if (userFeedWriter ==null) {
			model.addAttribute("title", "로그인을 해주세요");
			model.addAttribute("msg", "서비스 이용이 불가합니다");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/#");
			return "common/msg";
		} else {
			//Feed f = new Feed();
			// 유저 정보 가져오기
			User u = feedService.searchUser(userFeedWriter);
			// 또다른 유저로 들어가게 되면은 그 유저의 번호 가지고 있게 하자. ???

			// 페이지 구현 / 유저와 페이지에 들어가는 피드, 네비바 ,,,/유저가 올린 사진 가져오기 (사진 경로와 피드 번호)
			feedListData list = feedService.selectUserAllFeed(reqPage,  u);
			
			List<Feed> feedList = list.getList();
			List<Feed> filefath = new ArrayList<Feed>();
			for (Feed feedlist : feedList) {
				String filename = feedlist.getUserFeedFilepath();
				String savepath = "/feed/";
				String filepath = savepath + filename;
				Feed feed = new Feed();
				feed.setUserFeedFilepath(filepath);
				feed.setUserFeedNo(feedlist.getUserFeedNo());
				filefath.add(feed);
				// System.out.println(filepath);
			}

			model.addAttribute("list", filefath);
			model.addAttribute("pageNavi", list.getPageNavi());
			//f.setUser(u);
			model.addAttribute("user", u);
			return "/feed/list";
		}
	}

	// feed-list-writeBtn
	@PostMapping(value = "/feedWrite")
	public String feedWirte(Feed f, MultipartFile upfile1, MultipartFile upfile2, MultipartFile upfile3, Model model) {
			String savepath = root + "/feed/";
			List<FeedFile> fileList = new ArrayList<FeedFile>();
			MultipartFile[] upfile = { upfile1, upfile2, upfile3 };
			for (MultipartFile file : upfile) {
				if (!file.isEmpty()) {
					// 파일경로 복사, 중복된 이름 있으면 인덱스 작업
					String filepath = fileUtils.upload(savepath, file); // ice5_2.png
					FeedFile feedFile = new FeedFile();
					feedFile.setUserFeedFilepath(filepath);
					fileList.add(feedFile);
					//System.out.println(filepath);
				}
			}//for()
			//System.out.println(f); 
			
			//결과값 0 or feedNo  
			int result = feedService.insertfile(f, fileList);
			if (result > 0) {
				model.addAttribute("title", "작성성공!");
				model.addAttribute("msg", "글작성이 완료되었습니다.");
				model.addAttribute("icon", "success");
				model.addAttribute("loc", "/feed/view?userFeedNo="+result);
				return "common/msg";
			}else {
				model.addAttribute("title", "실패!");
				model.addAttribute("msg", "관리자에게 문의해주세요.");
				model.addAttribute("icon", "warning");
				model.addAttribute("loc", "/feed/myPage?userFeedWriter="+f.getUserFeedWriter()+"&reqPage=1");
				return "common/msg";
			}
		
	}

	@PostMapping(value = "/feedUpdate")
	public String feedUpdate(Feed f, MultipartFile upfile1, MultipartFile upfile2, MultipartFile upfile3, Model model) {
		//새로운 파일 경로 인덱스 작업 
		int result = 0;
		if(!upfile1.isEmpty()) {
			System.out.println("여기 있어요1");
		}
		if(!upfile2.isEmpty()) {
			System.out.println("여기 있어요2");
		}
		if(!upfile3.isEmpty()) {
			System.out.println("여기 있어요3");
		}
		if(!upfile1.isEmpty()||!upfile2.isEmpty()||!upfile3.isEmpty()) {
			MultipartFile[] newFiles = {upfile1, upfile2, upfile3};
			String savepath = root + "/feed/";
			List<FeedFile> newFileList = new ArrayList<FeedFile>();
			for (MultipartFile file : newFiles) {
				if (!file.isEmpty()) {
					// 파일경로 복사, 중복된 이름 검사 
					String filepath = fileUtils.upload(savepath, file); // ice5_2.png
					FeedFile feedFile = new FeedFile();
					feedFile.setUserFeedFilepath(filepath);
					newFileList.add(feedFile);//새로운 파일 경로 작업
					
				}
			}//for()
//			for(FeedFile file : newFileList) {
//				System.out.println("path="+file.getUserFeedFilepath());
//			}
			List<FeedFile> files = new ArrayList<FeedFile>();
			
		
			result = feedService.updateNewFile(f, newFileList);
				
			
		
			//result = feedService.updatefile(f, newFileList, files);
		}else {
			result = feedService.updateFeedContent(f);
		}
		
		
		

//		if(f.getFile1()!=null) {
//			FeedFile file = new FeedFile();
//			file.setUserFeedFilepath(f.getFile1());
//			files.add(file);
//		}
//		if(f.getFile2()!=null) {
//			FeedFile file = new FeedFile();
//			file.setUserFeedFilepath(f.getFile2());
//			files.add(file);
//		}
//		if(f.getFile3()!=null) {
//			FeedFile file = new FeedFile();
//			file.setUserFeedFilepath(f.getFile3());
//			files.add(file);
//		}


		if (result > 0) {
			model.addAttribute("title", "수정 성공!");
			model.addAttribute("msg", "게시글이 수정 되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/feed/view?userFeedNo="+f.getUserFeedNo());
			return "common/msg";
		}else {
			model.addAttribute("title", "실패!");
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/feed/myPage?userFeedWriter="+f.getUserFeedWriter());
			return "common/msg";
		}
	}

	// feed-view-delBtn
	@GetMapping(value = "/delete")
	public String delete(int userFeedNo, Model model) {
		int result = feedService.deleteFeed(userFeedNo);
		if (result > 0) {
			model.addAttribute("title", "게시글 삭제성공!");
			model.addAttribute("msg", "게시글 삭제 성공했습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/feed/myPage?reqPage=1");
			return "common/msg";
		} else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/feed/view?userFeedNo=" + userFeedNo);
			return "common/msg";
		}
	}

	// feed-view-updateBtn
	@GetMapping(value = "/updateFrm")
	public String updateFrm(String userFeedWriter, int userFeedNo, Model model) {
		Feed feed = feedService.selectUserOneFeed(userFeedNo);
		model.addAttribute("feed", feed);
		model.addAttribute("list", feed.getFeedList());
		//for (FeedFile file : feed.getFeedList()) {
			//System.out.println(file);
		//}
		return "/feed/updateFrm";
	}

}
