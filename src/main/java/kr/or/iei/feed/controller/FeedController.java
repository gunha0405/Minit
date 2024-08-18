package kr.or.iei.feed.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedComment;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.feed.model.dto.feedListData;
import kr.or.iei.feed.model.service.FeedService;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.text.model.dto.TextFeed;
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

	
	@GetMapping(value = "/writeForm")
	public String wirteForm() {
		return "feed/writeForm";
	}

	//feed-view 개인 회원 피드 페이지 
	@GetMapping(value = "/view")
	public String view(int userFeedNo, Model model, @SessionAttribute User user) {
		//해당 글 유저의 정보와 사진 파일리스트
		int userNo = user.getUserNo();

		Feed feed = feedService.selectUserOneFeed(userFeedNo, userNo); 

		//피드글 신고, 좋아요 보관함 여부 
		int reportCount = -1;
		int likeCount =-1;
		int repoCount = -1;
		String userId = user.getUserId();
		if(user != null) {
			reportCount = feedService.isReport(userFeedNo, userNo);
			likeCount = feedService.isLike(userFeedNo, userId);
			repoCount = feedService.isRepository(userFeedNo, userNo);
		}
		feed.setIsReport(reportCount);
		feed.setIsLike(likeCount);
		feed.setIsRepository(repoCount);
		model.addAttribute("feed", feed);
		model.addAttribute("list", feed.getFeedList());
		return "feed/view";
	}
	@ResponseBody
	@PostMapping(value="/userStorage")
	public List<Feed> userStorage(String userId, @SessionAttribute(required =false) User user, Model model) {
		int userNo = user.getUserNo();
		List<Feed> feedList = feedService.userStorage(userNo);
		//int totalCount = feedService.userStorage(userNo);
		return feedList;
	}

	@GetMapping(value = "/myPage")
	public String myPage(Integer reqPage, String userFeedWriter, Model model, @SessionAttribute(required =false) User user) {
		//팔로우 검사 
		if(user == null) {
			return"/";
		}
		int followBtn = -1; //
		if(user != null) {
			String loginUserId = user.getUserId();
			if(!userFeedWriter.equals(loginUserId)) { //로그인한 유저 != 피드주인 
				//팔로우 안했으면 0; 했으면 1;
				followBtn = feedService.selectFollowBtn(userFeedWriter, loginUserId);
			}			
		}
		
		
		reqPage =  (reqPage != null) ? reqPage : 1;
		
		//페이지 주인 정보 불러오기 -> 페이징 작업 
		User u = feedService.searchUser(userFeedWriter);
		feedListData list = feedService.selectUserAllFeed(reqPage,  u);
		List<Feed> feedList = list.getList();
			
		//게시물 마다 1개의 사진 정보 보내기 
		List<Feed> filefath = new ArrayList<Feed>();
		for (Feed feedlist : feedList) {
			String filename = feedlist.getUserFeedFilepath();
			String savepath = "/feed/";
			String filepath = savepath + filename;
			Feed feed = new Feed();
			feed.setUserFeedFilepath(filepath);
			feed.setUserFeedNo(feedlist.getUserFeedNo()); 
			filefath.add(feed);
		}//for
		//팔로잉, 팔로우 목록 
		Feed following = feedService.following(userFeedWriter);
		Feed follower = feedService.follower(userFeedWriter);	
	
		model.addAttribute("following", following);
		model.addAttribute("follower", follower);
		model.addAttribute("followingList", following.getUserList());
		model.addAttribute("followerList", follower.getUserList());
		model.addAttribute("list", filefath);
		model.addAttribute("pageNavi", list.getPageNavi());
		model.addAttribute("totalCount", list.getTotalCount());
		model.addAttribute("user", u);
		model.addAttribute("followBtn", followBtn);
		return "/feed/list";
		
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
	
				}
			}//for()
			
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
	public String feedUpdate(String originFilePath1, String originFilePath2, String originFilePath3, Feed f, int file1, int file2, int file3, MultipartFile upfile1, MultipartFile upfile2, MultipartFile upfile3, Model model) {
		//수정할 부분 화면에서 사진 경로 같이 숨겨서 보내서 그 부분만 수정하자 
		//새로운 파일 경로 인덱스 작업 
		String savepath = root + "/feed/";
		int result = 0;
		//미디어 input 파일이 있을 경우 
		if(!upfile1.isEmpty()) {
			String filepath = fileUtils.upload(savepath, upfile1);
			//기존파일과 새로운 파일의 이름이 같으면 업데이트 X
			if(originFilePath1 == filepath) {
				result += 0;
			}else if(originFilePath1 != filepath){
				//기존파일과 새로운 파일의 이름이 다르면 업데이트
				result += feedService.updateFeedFilepath(file1, filepath);				
			}
		}else {//미디어 input 파일이 없을 경우
			//originFilePath 값이 없다면 해당 파일 null로 수정
			if(originFilePath1.equals("filepathNull")) {
				result = feedService.updateFeedFilepathNull(file1);
			}else if(!originFilePath1.equals("filepathNull")) {
				result = +0;
			}
		}
		
		
		if(!upfile2.isEmpty()) {
			String filepath = fileUtils.upload(savepath, upfile2);
			//기존파일과 새로운 파일의 이름이 같으면 업데이트 X
			if(originFilePath1 == filepath) {
				result += 0;
			}else if(originFilePath2 != filepath){
				//기존파일과 새로운 파일의 이름이 다르면 업데이트
				result += feedService.updateFeedFilepath(file2, filepath);				
			}
		}else {//미디어 input 파일이 없을 경우
			//originFilePath 값이 없다면 해당 파일 null로 수정
			if(originFilePath3.equals("filepathNull")) {
				result = feedService.updateFeedFilepathNull(file2);
			}else if(!originFilePath1.equals("filepathNull")) {
				result = +0;
			}
		}
		
		if(!upfile3.isEmpty()) {
			String filepath = fileUtils.upload(savepath, upfile3);
			//기존파일과 새로운 파일의 이름이 같으면 업데이트 X
			if(originFilePath3 == filepath) {
				result += 0;
			}else if(originFilePath3 != filepath){
				//기존파일과 새로운 파일의 이름이 다르면 업데이트
				result += feedService.updateFeedFilepath(file3, filepath);				
			}
		}else {//미디어 input 파일이 없을 경우
			//originFilePath 값이 없다면 해당 파일 null로 수정
			if(originFilePath3.equals("filepathNull")) {
				result = feedService.updateFeedFilepathNull(file3);
			}else if(!originFilePath1.equals("filepathNull")) {
				result = +0;
			}
		}
		
		result = feedService.updateFeedContent(f);

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
		return "/feed/updateFrm";
	}
	
	@ResponseBody
	@PostMapping(value="/commentWrite")
	public FeedComment commentWrite(@SessionAttribute(required =false) User user, String feedCommentContent, int feedRef ) {
		String userId = user.getUserId();
		//유저 정보 가져오기 
		FeedComment fc = feedService.insertFeedComment(userId, feedCommentContent, feedRef);
		return fc;
	}
	
	@ResponseBody
	@PostMapping(value="/userFollowCancel")
	public int userFollowCancel(String loginUser, String writerUser) {
		int num = feedService.userFollowCancel(loginUser, writerUser);

		if(num > 0) {
			num = feedService.followerNo(writerUser);
		}
		return num;
	}

	@ResponseBody
	@PostMapping(value="/userFollow")
	public int userFollow(String loginUser, String writerUser) {
		int num = feedService.userFollow(loginUser, writerUser);
		if(num > 0) {
			num = feedService.followerNo(writerUser);
		}

		return num;
	}
	
	@ResponseBody
	@PostMapping(value="/feedCommentDelete")
	public int feedCommentDelete(int feedCommentNo) {
		int result = feedService.feedCommentDelete(feedCommentNo);
		return result;
	}
	@ResponseBody
	@PostMapping(value="/commentUpdate")
	public int commentUpdate(int feedCommentNo, String updatedContent ) {
		int result = feedService.feedCommentUpdate(feedCommentNo, updatedContent);
		return result;
	}
	
	@ResponseBody
	@PostMapping(value="/reportFeed")
	public int reportFeed(int userFeedNo, @SessionAttribute(required =false) User user) {
		 if (user == null) {
		     return -10; // 로그인하지 않은 경우
		}else {
		    int userNo = user.getUserNo();
		    int result = feedService.reportFeed(userFeedNo, userNo);
		    return result;
		}
	}
	
	@ResponseBody
	@PostMapping(value="/isLike")
	public int isLike(int userFeedNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
		    String userId = user.getUserId();
		    int result = feedService.feedLike(userFeedNo, userId);
		    return result;
		}
	}
	@ResponseBody
	@PostMapping(value="/isLikeCancel")
	public int isLikeCancel(int userFeedNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
		    String userId = user.getUserId();
		    int result = feedService.feedLikeCancel(userFeedNo, userId);
		    return result;
		}
	}
	@ResponseBody
	@PostMapping(value="/commentLike")
	public int commentLike(int feedCommentNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
			int userNo = user.getUserNo();
			int result = feedService.commentLike(feedCommentNo, userNo);
			return result;
		}
	}
	@ResponseBody
	@PostMapping(value="/commentLikeCancel")
	public int commentLikeCancel(int feedCommentNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
		    int userNo = user.getUserNo();
		    int result = feedService.feedLikeCancel(feedCommentNo, userNo);
		    return result;
		}
	}
	@ResponseBody
	@PostMapping(value="/repoIn")
	public int repoIn(int userFeedNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
		    int userNo = user.getUserNo();
		    int result = feedService.feedRepoIn(userFeedNo, userNo);
		    return result;
		}
	}
	@ResponseBody
	@PostMapping(value="/repoOut")
	public int repoOut(int userFeedNo, @SessionAttribute(required =false) User user) {
		if(user == null) {
			return -1;
		}else {
		    int userNo = user.getUserNo();
		    int result = feedService.feedRepoOut(userFeedNo, userNo);
		    return result;
		}
	}

    @GetMapping(value="/selectReportFeed")
    public String selectReportFeed(Model model) {
    	List<Feed> reportList = feedService.selectReportFeed();
    	
    	model.addAttribute("reportList", reportList);
    	return "/admin/warningFeed";
    }

	@ResponseBody
	@PostMapping(value="/userStorageAll")
	public Feed userStorageFeed(String userId){
		Feed feedList = feedService.storageAll(userId); 
		return feedList;
	}
	@ResponseBody
	@PostMapping(value="/feedAll")
	public List<Feed> feedAll(String userId){
		List<Feed> feedList = feedService.feedAll(userId); 
		return feedList;
	}
	@ResponseBody
	@PostMapping(value="/photoAll")
	public List<Photo> photoAll(String userId){
		List<Photo> photoList = feedService.photoAll(userId); 
		return photoList;
	}
	@ResponseBody
	@PostMapping(value="/textAll")
	public List<TextFeed> textAll(String userId){
		List<TextFeed> textList = feedService.textAll(userId); 
		return textList;
	}
}
