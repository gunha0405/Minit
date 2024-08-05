package kr.or.iei.text.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import kr.or.iei.text.model.service.TextService;


@Controller
@RequestMapping(value="/text")
public class TextController {

    @Autowired
    private TextService textService;
    
    @GetMapping(value="/textList")
    public String textList(Model model) {
        List textFeedList = textService.selectTextFeed();
        model.addAttribute("textFeedList", textFeedList);
        
        return "text/textFeed";
    }
    
    @GetMapping(value="/textFeedWrite")
    public String textFeedWrite() {
        return "";
    }
}