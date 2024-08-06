package kr.or.iei.text.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedJsonList;
import kr.or.iei.text.model.service.TextService;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserImg;
import kr.or.iei.user.model.service.UserService;


@Controller
@RequestMapping(value="/text")
public class TextController {

    @Autowired
    private TextService textService;
    @Autowired
    private UserService userService;
    
    @GetMapping(value="/textFeedList")
    public String textList(Model model) {
        List textFeedList = textService.selectTextFeed();
        model.addAttribute("textFeedList", textFeedList);
        
        return "text/textFeed";
    }
    
    @ResponseBody
    @GetMapping(value="/textFeedWrite")
    public TextFeedJsonList textFeedWrite(String textFeedContent, @SessionAttribute(required =false) User user) {
    	int result = textService.insertTextFeed(textFeedContent, user);
    	if(result > 0) {
    		//json 리턴
    		//글정보 + user 정보 + userImg 정보 리턴
    		int textFeedNo = textService.getTextFeedNo();
    		TextFeed textFeed = textService.selectOneTextFeed(textFeedNo); 
    		User writerUser = userService.selectOneUser(user);
    		TextFeedJsonList textFeedJsonList = new TextFeedJsonList(writerUser, textFeed);
    		return textFeedJsonList;
    	} else {
    		//null 리턴
    		return null;
    	}
    }
    
    @ResponseBody
    @GetMapping(value="/deleteTextFeed")
    public int deleteTextFeed(int textFeedNo) {
    	int result = textService.deleteTextFeed(textFeedNo);
    	
    	return result;
    }
}