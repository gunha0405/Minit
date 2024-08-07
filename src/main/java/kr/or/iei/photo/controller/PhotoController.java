package kr.or.iei.photo.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.service.PhotoService;
import kr.or.iei.user.model.dto.User;
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
		List photoFeedList = photoService.selectPhotoFeed();
		model.addAttribute("list",photoFeedList);
		return "photo/list";
	}
	
	@ResponseBody
	@GetMapping(value="/more")
	public List photoMore() {
		List photoList = photoService.selectPhotoFeed();
		return photoList;
	}
	
	@PostMapping(value="/write")
	public String write(Photo p ,MultipartFile imageFile,Model model,@SessionAttribute(required =false) User user) {
		String savepath= root+"/photo/";
		String filepath = fileUtils.upload(savepath, imageFile);
				p.setPhotoFeedImg(filepath);
		int result = photoService.insertPhoto(p,user);
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
	
	@GetMapping(value="/delete")
	@ResponseBody
	public int delete(int photoFeedNo) {
		int result = photoService.deletePhoto(photoFeedNo);
		return result;
	}
	
	@PostMapping(value="/update")
	@ResponseBody
	public int update(int photoFeedNo,MultipartFile imageFile) {
		String savePath = root + "/photo/";
        String filePath = fileUtils.upload(savePath, imageFile);
        
        if (filePath == null || filePath.isEmpty()) {
            return 0; // 파일 경로가 유효하지 않으면 업데이트하지 않음
        }
        
        Photo p = new Photo();
        p.setPhotoFeedNo(photoFeedNo);
        p.setPhotoFeedImg(filePath);
        int result = photoService.updatePhoto(p);
        return result;
	}
	
}
