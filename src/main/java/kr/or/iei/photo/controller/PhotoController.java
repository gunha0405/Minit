package kr.or.iei.photo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.service.PhotoService;
import kr.or.iei.util.FileUtils;

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
	@PostMapping(value="/write")
	public String write(MultipartFile imageFile,Model model) {
		String savepath= root+"/photo/";
		String filepath = fileUtils.upload(savepath, imageFile);
				Photo p = new Photo();
				p.setPhotoFeedImg(filepath);
		int result = photoService.insertPhoto(p);
		if(result>0) {
			model.addAttribute("title","작성완료");
			model.addAttribute("msg","작성되었습니다.");
			model.addAttribute("icon","success");

		}else {
			model.addAttribute("title","작성실패");
			model.addAttribute("msg","작성되었습니다.");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/photo/list");
		return "common/msg";
	}
	@PostMapping(value="/insertComment")
	public String insertComment(PhotoComment pc,Model model) {
		int result = PhotoService.insertComment(pc);
		if(result > 0){
			model.addAttribute("title","댓글 작성 성공");
			model.addAttribute("msg","댓글이 작성되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","댓글 작성 실패");
			model.addAttribute("msg","댓글이 작성되었습니다.");
			model.addAttribute("icon","warning");
		}
		model.addAttribute("loc","/photo/list");
		return "common/msg";
	}
}
