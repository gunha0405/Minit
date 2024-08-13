package kr.or.iei.text.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;


import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedComment;
import kr.or.iei.text.model.dto.TextFeedCommentJsonList;
import kr.or.iei.text.model.dto.TextFeedJsonList;
import kr.or.iei.text.model.service.TextService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;


@Controller
@RequestMapping(value="/text")
public class TextController {

    @Autowired
    private TextService textService;
    @Autowired
    private UserService userService;
    
    @GetMapping(value="/textFeedList")
    public String textList(Model model, @SessionAttribute(required =false) User user) {
        int userNo = user != null ? user.getUserNo() : 0;
        List<TextFeed> textFeedList = textService.selectTextFeed(userNo);
        model.addAttribute("textFeedList", textFeedList);
        return "text/textFeed";
    }
    
    @ResponseBody
    @PostMapping(value="/textFeedWrite")
    public TextFeedJsonList textFeedWrite(String textFeedContent, @SessionAttribute(required =false) User user) {
    	int result = textService.textFeedWrite(textFeedContent, user);
    	if(result > 0) {
    		//json 리턴
    		//글정보 + user 정보 + userImg 정보 리턴
    		int textFeedNo = textService.getTextFeedNo();
    		TextFeed textFeed = textService.selectOneTextFeed(textFeedNo); 
    		User writerUser = userService.selectOneUser(user);
    		textFeed.setTextFeedWriterImg(writerUser.getUserImg());
    		TextFeedJsonList textFeedJsonList = new TextFeedJsonList(writerUser, textFeed);
    		return textFeedJsonList;
    	} else {
    		//null 리턴
    		return null;
    	}
    }
    
    @ResponseBody
    @PostMapping(value="/deleteTextFeed")
    public int deleteTextFeed(int textFeedNo) {
    	int result = textService.deleteTextFeed(textFeedNo);
    	
    	return result;
    }
    
    @ResponseBody
    @PostMapping(value="/textFeedCommentWrite")
    public TextFeedCommentJsonList textFeedCommentWrite(@SessionAttribute(required =false) User user,String textFeedCommentContent,int textFeedNo) {
    	int result = textService.textFeedCommentWrite(textFeedCommentContent, textFeedNo,user);
    	if(result > 0) {
    		//json 리턴
    		//댓글정보 + user 정보 + userImg 리턴
    		int textFeedCommentNo = textService.getTextFeedCommentNo();
    		TextFeedComment textFeedComment = textService.selectOnetTextFeedComment(textFeedCommentNo);
    		User commentWriterUser = userService.selectOneUser(user);
    		textFeedComment.setTextFeedCommentWriterImg(commentWriterUser.getUserImg());
    		TextFeedCommentJsonList textFeedCommentJsonList = new TextFeedCommentJsonList(commentWriterUser, textFeedComment);
    		return textFeedCommentJsonList;
    	}else {    		
    		return null;
    	}
    }
    
    @ResponseBody
    @PostMapping(value="/deleteTextFeedComment")
    public int deleteTextFeedComment(int textFeedCommentNo) {
    	int result = textService.deleteTextFeedComment(textFeedCommentNo);
    	return result;
    }
    
    @ResponseBody
    @PostMapping(value="/editTextFeed")
    public int editTextFeed(String textFeedEditContent, int textFeedEditNo) {
    	int result = textService.editTextFeed(textFeedEditContent, textFeedEditNo);
    	return result;
    }
    
    @ResponseBody
    @PostMapping(value="/editTextFeedComment")
    public int editTextFeedComment(String textFeedCommentEditContent, int textFeedCommentEditNo) {
    	int result = textService.editTextFeedComment(textFeedCommentEditContent, textFeedCommentEditNo);
    	return result;
    }
    
    @ResponseBody
    @PostMapping(value="/textFeedLikePush")
    public int textFeedLikePush(int textFeedNo, int isLike,@SessionAttribute(required =false) User user) {
    	if(user == null) {
    		return -10;
    	}else {
    		int userNo = user.getUserNo();
    		int likeCount = textService.textFeedLikePush(textFeedNo, isLike, userNo);
    		//int likeCount = textService.likeCount(textFeedNo);
    		return likeCount;
    	}
    }
    
    @ResponseBody
    @PostMapping(value="/textFeedCommentLikePush")
    public int textFeedCommentLikePush(int textFeedCommentNo, int isLike, @SessionAttribute(required =  false) User user) {
    	if(user == null) {
    		return -10;
    	}else {
    		int userNo = user.getUserNo();
    		int likeCount = textService.textFeedCommentLikePush(textFeedCommentNo, isLike, userNo);
    		return likeCount;
    	}    	
    }
    
    @ResponseBody
    @PostMapping(value="/reportTextFeed")
    public int reportTextFeed(int textFeedNo,int isReport ,@SessionAttribute(required = false) User user) {
    	if(user == null) {
    		return -10;
    	}else {
    		int userNo = user.getUserNo();
    		int result = textService.textFeedReport(textFeedNo, userNo, isReport);
    		return result;
    	}
    }
    
    @ResponseBody
    @GetMapping(value="/textFeedSave")
    public int textFeedSave(int textFeedNo, int isSave, @SessionAttribute(required = false) User user) {
    	if(user == null) {
    		return -10;
    	}else {
    		int userNo = user.getUserNo();
    		int result = textService.textFeedSave(textFeedNo, userNo, isSave);
    		return result;
    	}
    }
    
    @GetMapping(value="/selectReportFeed")
    public String selectReportFeed(Model model) {
    	List<TextFeed> reportList = textService.selectReportFeed();
    	
    	model.addAttribute("reportList", reportList);
    	return "/admin/warningText";
    }
    
}