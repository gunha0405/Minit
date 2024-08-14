package kr.or.iei;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.util.FileUtils;


@Controller

public class HomeController {

    @Autowired
    private BoardService boardService;

    @Value("${file.root}")
    private String root;

    @Autowired
    private FileUtils fileUtils;

    @GetMapping(value = "/")
    public String main() {
        return "index";
    }

    @GetMapping("/index")
    @ResponseBody
    public Board getAllBoards() {
        Board b = new Board();
    	List<Photo> photolist = boardService.photoList();
        List<Feed> feedlist  = boardService.boardList();
    	b.setPhotolist(photolist);
    	b.setFeedlist(feedlist);
        return b; // 단순히 게시판 리스트를 반환할거에여
    }
    
    
    @GetMapping("/best")
    @ResponseBody
    public List<Board> BestBoards(){
    	List<Board> list = boardService.BestBoards();
    	return list;
    }
    @GetMapping("/following")
    @ResponseBody
    public List<Board> followingBoards(@SessionAttribute(required = false)User user){
    	String userId = user.getUserId();
    	
    	ArrayList<String> followingid = boardService.searchFollowingId(userId);
 
    	List<Board> list = boardService.followingBoards(followingid);
    	
    	return list;
    }
   @ResponseBody
   @GetMapping("/searchList")
   public Board searchList(User user){
	   Board b = new Board();
	   List<Photo> list = boardService.searchList(user);
	   List<Feed> list2 = boardService.searchList2(user);
	   b.setPhotolist(list);
	   b.setFeedlist(list2);
	   return b;
   }


   @GetMapping("/etc/about")
   public String showAboutPage() {
       return "about"; // about.html을 렌더링
   }
}

   
    
    
