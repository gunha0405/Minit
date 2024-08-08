package kr.or.iei.photo.controller;

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
    public String list(Model model, @SessionAttribute(required = false) User user) {
        int userNo = user != null ? user.getUserNo() : 0;
        List photoFeedList = photoService.selectPhotoFeed(userNo);
        System.out.println(photoFeedList);
        model.addAttribute("list", photoFeedList);
        return "photo/list";
    }

    @ResponseBody
    @GetMapping(value="/more")
    public List photoMore(@SessionAttribute(required = false) User user) {
        int userNo = user != null ? user.getUserNo() : 0;
        List photoList = photoService.selectPhotoFeed(userNo);
        return photoList;
    }

    @PostMapping(value="/write")
    public String write(Photo p, MultipartFile imageFile, Model model, @SessionAttribute(required = false) User user) {
        if (user == null) {
            model.addAttribute("title", "작성실패");
            model.addAttribute("msg", "로그인이 필요합니다.");
            model.addAttribute("icon", "error");
            model.addAttribute("loc", "/photo/list");
            return "common/msg";
        }

        String savePath = root + "/photo/";
        String filePath = fileUtils.upload(savePath, imageFile);
        p.setPhotoFeedImg(filePath);
        int result = photoService.insertPhoto(p, user);
        if (result > 0) {
            model.addAttribute("title", "작성완료");
            model.addAttribute("msg", "작성되었습니다.");
            model.addAttribute("icon", "success");
        } else {
            model.addAttribute("title", "작성실패");
            model.addAttribute("msg", "작성에 실패했습니다.");
            model.addAttribute("icon", "error");
        }
        model.addAttribute("loc", "/photo/list");
        return "common/msg";
    }
    
    @PostMapping(value="/insertComment")
    @ResponseBody
    public String insertComment(PhotoComment pc, Model model, @SessionAttribute(required = false) User user, int photoFeedNo) {
        if (user == null) {
            model.addAttribute("loc", "/photo/list");
            return "common/msg";
        }
        int result = photoService.insertComment(pc, user, photoFeedNo);
        model.addAttribute("loc", "/photo/list");
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
    public int update(int photoFeedNo, MultipartFile imageFile) {
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

    @ResponseBody
    @PostMapping(value="/likePush")
    public int likePush(int photoFeedNo, int isLike, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10;
        } else {
            int userNo = user.getUserNo();
            int result = photoService.likePush(photoFeedNo, isLike, userNo);
            return result;
        }
    }

    @ResponseBody
    @GetMapping(value="/likeStatus")
    public int likeStatus(int photoFeedNo, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10; // 로그인 필요
        } else {
            int userNo = user.getUserNo();
            boolean isLiked = photoService.isLikecheck(photoFeedNo, userNo);
            return isLiked ? 1 : 0;
        }
    }
    /*
     댓글 
      */
    @GetMapping(value="comments")
    @ResponseBody
    public List<PhotoComment> getComment(int photoFeedNo) {
    	List cl = photoService.getCommentList(photoFeedNo);
    	return cl;
    }
    
    @PostMapping(value="/updateComment")
    @ResponseBody
    public String updateComment(PhotoComment photoFeedCommentContent,Model model) {
    	int result = photoService.updateComment(photoFeedCommentContent);
    	model.addAttribute("loc","/photo/list");
    	return "common/msg";
    }
    @GetMapping(value="/deleteComment")
    @ResponseBody
    public String deleteComment(PhotoComment pc,Model model) {
    	int result = photoService.deleteComment(pc);
    	model.addAttribute("loc","/photo/list");
    	return "common/msg";
    }
    @ResponseBody
    @PostMapping(value="/report")
    public int contentsDec(int photoFeedNo, int isDec, @SessionAttribute(required = false) User user) {
    	if (user == null) {
            return -10;
        } else {
            int userNo = user.getUserNo();
            int result = photoService.contentsDec(photoFeedNo, isDec, userNo);
            return result;
        }
    }
}
