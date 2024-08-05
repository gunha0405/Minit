package kr.or.iei.photo.controller;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import kr.or.iei.photo.model.service.PhotoService;

@Controller
@RequestMapping(value="/photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/list")
	public String list(Model model) {
		return "photo/list";
	}
}
