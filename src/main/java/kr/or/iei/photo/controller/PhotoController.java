package kr.or.iei.photo.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.etc.dto.Foodad;
import kr.or.iei.photo.model.service.PhotoService;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.service.UserService;
import kr.or.iei.util.FileUtils;

@Controller
@RequestMapping(value = "/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @Value("${file.root}")
    private String root;

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/list")
    public String list(Model model, @SessionAttribute(required = false) User user) {
        int userNo = user != null ? user.getUserNo() : 0;
        List<Photo> photoFeedList = photoService.selectPhotoFeed(userNo);
        model.addAttribute("list", photoFeedList);
        return "photo/list";
    }

    @ResponseBody
    @GetMapping(value = "/more")
    public List<Photo> photoMore(@SessionAttribute(required = false) User user) {
        int userNo = user != null ? user.getUserNo() : 0;
        List<Photo> photoList = photoService.selectPhotoFeed(userNo);
        return photoList;
    }

    @PostMapping(value = "/write")
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

    @PostMapping(value = "/insertComment")
    @ResponseBody
    public String insertComment(PhotoComment pc, Model model, @SessionAttribute(required = false) User user,
            int photoFeedNo) {
        if (user == null) {
            model.addAttribute("loc", "/photo/list");
            return "common/msg";
        }
        int result = photoService.insertComment(pc, user, photoFeedNo);
        model.addAttribute("loc", "/photo/list");
        int photoFeedCommentNo = photoService.getPhotoFeedCommentNo();
        PhotoComment photoComment = photoService.selectOnePhotoFeedComment(photoFeedCommentNo);
        User commentWriterUser = userService.selectOneUser(user);
        photoComment.setPhotoFeedCommentWriterImg(commentWriterUser.getUserImg());
        
        return "common/msg";
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public int delete(int photoFeedNo, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10; // 로그인되지 않음
        }
        Photo photo = photoService.getPhotoById(photoFeedNo);
        if (photo == null || !photo.getPhotoFeedWriter().equals(user.getUserId())) {
            return -1; // 권한 없음
        }
        int result = photoService.deletePhoto(photoFeedNo);
        return result;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public int update(int photoFeedNo, MultipartFile imageFile, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10; // 로그인되지 않음
        }
        Photo photo = photoService.getPhotoById(photoFeedNo);
        if (photo == null || !photo.getPhotoFeedWriter().equals(user.getUserId())) {
            return -1; // 권한 없음
        }

        String savePath = root + "/photo/";
        String filePath = fileUtils.upload(savePath, imageFile);

        if (filePath == null || filePath.isEmpty()) {
            return 0; // 유효하지 않은 파일 경로
        }

        photo.setPhotoFeedImg(filePath);
        int result = photoService.updatePhoto(photo);
        return result;
    }

    @ResponseBody
    @PostMapping(value = "/likePush")
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
    @GetMapping(value = "/likeStatus")
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
     * 댓글
     */
    @GetMapping(value = "/comments")
    @ResponseBody
    public List<PhotoComment> getComment(int photoFeedNo) {
        List<PhotoComment> cl = photoService.getCommentList(photoFeedNo);
        return cl;
    }

    @PostMapping(value = "/updateComment")
    @ResponseBody
    public String updateComment(PhotoComment photoFeedCommentContent, Model model) {
        int result = photoService.updateComment(photoFeedCommentContent);
        model.addAttribute("loc", "/photo/list");
        return "common/msg";
    }

    @GetMapping(value = "/deleteComment")
    @ResponseBody
    public int deleteComment(PhotoComment photoFeedCommentNo) {
        int result = photoService.deleteComment(photoFeedCommentNo);
        return result;
    }

    @ResponseBody
    @PostMapping(value = "/report")
    public int contentsDec(int photoFeedNo, int isDec, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10;
        } else {
            int userNo = user.getUserNo();
            int result = photoService.contentsDec(photoFeedNo, userNo);
            return result;
        }
    }

    @RestController // controller에 responesbody가 섞인것
    @RequestMapping("/user")
    public class UserController {
        @GetMapping("/checkLoginStatus")
        public boolean checkLoginStatus(HttpSession session) {
            // 로그인 상태를 확인하는 로직을 여기에 구현
            User user = (User) session.getAttribute("user");
            return user != null;
        }
    }
    
    
    @GetMapping("/photo")
    public String photoPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("sessionUserId", user.getUserId());
        }
        return "photo";
    }
    

    @ResponseBody
    @PostMapping(value = "/save")
    public int save(int photoFeedNo, int isSave, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10;
        } else {
            int userNo = user.getUserNo();
            int result = photoService.save(photoFeedNo, isSave, userNo);
            return result;
        }
    }

    @ResponseBody
    @GetMapping(value = "/saveStatus")
    public int saveStatus(int photoFeedNo, @SessionAttribute(required = false) User user) {
        if (user == null) {
            return -10; // 로그인 필요
        } else {
            int userNo = user.getUserNo();
            boolean isSave = photoService.saveCheck(photoFeedNo, userNo);
            return isSave ? 1 : 0;
        }
    }

    @GetMapping(value = "adPlace")
    @ResponseBody
    public List<Foodad> adPlace(String pageNo) {
        String url = "https://apis.data.go.kr/6260000/FoodService/getFoodKr";
        String serviceKey = "EHvashpnNkdP1E98NdKt2l+KhCqEdg1mH6puKHX4iiZh2vQm1TfXgmZPkAOe1q0p+xCgaDjMjlMB2QOJ2cTLIQ=="; // 실제 키로 대체
        String numOfRows = "10";
        String resultType = "json";
        List<Foodad> list = new ArrayList<>();

        try {
            String result = Jsoup.connect(url)
                                .data("serviceKey", serviceKey)
                                .data("pageNo", pageNo)
                                .data("numOfRow", numOfRows)
                                .data("resultType", resultType)
                                .ignoreContentType(true)
                                .get()
                                .text();

            JsonReader reader = new JsonReader(new StringReader(result));
            reader.setLenient(true); // 약간의 JSON 구문 오류를 허용
            JsonElement rootElement = JsonParser.parseReader(reader);

            // JSON 응답이 실제로 객체인지 확인
            if (rootElement.isJsonObject()) {
                JsonObject object = rootElement.getAsJsonObject();

                // getFoodKr 요소가 실제로 JsonObject인지 확인
                JsonElement getFoodKrElement = object.get("getFoodKr");
                if (getFoodKrElement != null && getFoodKrElement.isJsonObject()) {
                    JsonObject getFoodKr = getFoodKrElement.getAsJsonObject();
                    JsonArray items = getFoodKr.get("item").getAsJsonArray();

                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        String mainTitle = item.get("MAIN_TITLE").getAsString();
                        String mainImg = item.get("MAIN_IMG_THUMB").getAsString();
                        String address = item.get("ADDR1").getAsString();
                        String tel = item.get("CNTCT_TEL").getAsString();
                        String intro = item.get("ITEMCNTNTS").getAsString();
                        Foodad fa = new Foodad(mainTitle, mainImg, address, tel, intro);
                        list.add(fa);
                    }
                } else {
                    // JSON 데이터가 예상과 다를 때 로그 출력 또는 예외 처리
                    System.err.println("Expected JsonObject for 'getFoodKr', but found: " + getFoodKrElement);
                }
            } else {
                // JSON 루트가 객체가 아닐 때 로그 출력 또는 예외 처리
                System.err.println("Expected JsonObject as root element, but found: " + rootElement);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @GetMapping(value = "/ad")
    public String ad() {
        return "photo/list";
    }
    
    
    @GetMapping(value="/selectReportFeed")
    public String selectReportFeed(Model model) {
    	List<Photo> reportList = photoService.selectReportFeed();
    	model.addAttribute("reportList", reportList);
    	return "/admin/warningPhoto";
    }
    

}