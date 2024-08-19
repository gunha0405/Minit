package kr.or.iei.feed.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.feed.model.dao.FeedDao;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedComment;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.feed.model.dto.feedListData;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;

@Service
public class FeedService {
	@Autowired
	private FeedDao feedDao;
	@Value("${file.root}")
	private String root;

	public User searchUser(String userId) {
		User u = feedDao.searchUser(userId);
		return u;
	}

	//게시물과 사진 insert 
	@Transactional
	public int insertfile(Feed f, List<FeedFile> fileList) {
		int result = 0;
		result = feedDao.insertFeed(f);

		// 최대 첨부파일 갯수
		int fileSize = 3;
		// feed내용 성공하면 올린 사진 갯수 만큼 넣는다
		int fileListNum = fileList.size();
		
		if (result > 0) {
			// insert할때 생성된 번호를 조회
			int feedNo = feedDao.selectFeedNo();
			// 반복문으로 사진 크기만큼 feed_file 테이블 insert
			for(FeedFile feedFile : fileList) {
				// insert될때 생성된 공지사항번호를 저장한 후 feed_file insert 요청
				feedFile.setUserFeedNo(feedNo);
				result += feedDao.insertFeedFile(feedFile);
			}
			//입력받는 파일이 세개의 파일보다 적을때 
			if(fileSize > fileListNum) {
				for(int i = 0; i < fileSize-fileListNum; i++) {
					result += feedDao.inserFeedNull(feedNo);
				}
			}
			if(result > 0 ) {
				return feedNo;
			}
		}
		return result;
	}

	//페이징 작업
	public feedListData selectUserAllFeed(int reqPage, User u) {


		// reqPage : 사용자가 요청한 페이지 번호
		// 한 페이지당 보여줄 게시물의 수(지정) -> 8개
		int numPerPage = 8;
		// 사용자가 요청한 페이지에 따른 게시물 시작번호/끝번호 계산
		// reqPage == 1 -> start = 1 / end = 8
		// reqPage == 2 -> start = 9 / end = 16
		// reqPage == 3 -> start = 17 / end = 24
		// reqPage == 4 -> start = 25 / end = 32
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;

		// String userId = u.getUserId();
		// 요청페이지에 필요한 공지사항 목록을 조회(파일 패스랑 , 파일 번호 저장)
		List<Feed> feedList = new ArrayList<Feed>();


		// 피드 갯수 만큼 for문 돌게
		int feedNum = feedDao.selectFeedList(start, end, u);
		if(feedNum >0) {
			for (int i = 0; i < feedNum; i++) {
				// 리스트에 담을 번호
				int feedNo = feedDao.searhFeedNo(start, end, u, i + 1);
				// 리스트 번호로 파일 가져오기
				String filefath = feedDao.feedFilepath(start, end, u, feedNo);
				Feed f = new Feed();
				f.setUserFeedFilepath(filefath);
				f.setUserFeedNo(feedNo);
				feedList.add(f);
			}
		}

		// 페이지 네비게이션 제작(사용자가 클릭해서 다른 페이지를 요청할 수 있도록 하는 요소 ) 제작
		// 페이지 네비게이션을 서비스에서 만드는 이유 -> 총 게시물수를 조회해와야 가능하기 떄문
		// select count(*) from notice;
		int totalCount = 0;
		totalCount = feedDao.selectFeedTotalCount(u);
		// 전체 페이지 수 계산
		int totalPage = 0;
		if (totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;
		} else {
			totalPage = totalCount / numPerPage + 1;
		}

		// 페이지 네비 사이즈 지정
		int pageNaviSize = 5;

		// 페이지너비 시작번호를 지정
		// reqPage 1~5 : 1 2 3 4 5
		// reqPage 6~10 : 6 7 8 9 10
		// reqPage 11~15 : 11 12 13 14 15

		int pageNo = (reqPage - 1) / pageNaviSize * pageNaviSize + 1;
		// html 코드 작성(페이지 네비게이션 작성)
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버턴(1페이지로 시작하지 않으면)
		if (pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item' href='/feed/myPage?userFeedWriter=" + u.getUserId() + "&reqPage="
					+ (pageNo - 1) + "'>";
			pageNavi += "<span class = 'material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}

		for (int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if (pageNo == reqPage) {
				pageNavi += "<a class ='page-item active-page' href='/feed/myPage?userFeedWriter=" + u.getUserId()
						+ "&reqPage=" + pageNo + "'>";

			} else {
				pageNavi += "<a class ='page-item' href='/feed/myPage?userFeedWriter=" + u.getUserId() + "&reqPage="
						+ pageNo + "'>";

			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			// 페이지를 만들다가 최종페이지를 출력했으면 더이상 반복하지 않고 종료
			if (pageNo > totalPage) {
				break;
			}
		}
		// 다음버튼 생성(최종 페이지를 출력하지 않았으면)
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item' href='/feed/myPage?userFeedWriter=" + u.getUserId() + "$reqPage="
					+ pageNo + "'>";
			pageNavi += "<span class = 'material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		// 컨트롤러로 되돌려줄 데이터가 공지사항 목록, 만든 페이지 네비게이션
		// -> java의 메소드는 1개의 자료형만 리턴 가능 -> 2개를 되돌려줘야함 List, String
		// -> 되돌려주고 싶은 데이터 2개를 저장할 수 있는 객체를 생성해서 객체로 묶어서 하나로 리턴
		feedListData fld = new feedListData(feedList, pageNavi, totalCount);		
		return fld;
	}
	
	// 피드 게시물 보기 
	public Feed selectUserOneFeed(int userFeedNo, int userNo) {
		//게시물정보 입력받기
		Feed feed = feedDao.searchFeedUser(userFeedNo);
		//유저 이미지
		String userImg = feedDao.totlaImg(userFeedNo);

		feed.setUserFeedFilepath("/user/"+userImg);

		//사진 게시물 저장 
		// 게시물당 사진 게시물 몇개인지
		int totalImgNo = feedDao.totalImg(userFeedNo);
		List<FeedFile> filepathList = new ArrayList<FeedFile>();
		for(int i = 0; i < totalImgNo; i ++) {
			FeedFile f = new FeedFile();
			String filepath = feedDao.getFilePath(userFeedNo, i+1);
			f.setUserFeedFilepath("/feed/"+filepath);
			filepathList.add(f);
		}
		feed.setFeedList(filepathList);

		// 게시물 갯수만큼 배열만큼 사진저장
		//List<FeedFile> feedList = new ArrayList<FeedFile>();
		//for (int i = 0; i < totalImgNo; i++) {
		//	String filepath = feedDao.searchFeedImg(userFeedNo, i);
		//	FeedFile fF = new FeedFile();
		//	fF.setUserFeedFilepath(filepath);
		//	feedList.add(fF);
		//}
		//feed.setFeedList(feedList);
		
		//댓글 가져오기 
		List<FeedComment> feedCommentList = new ArrayList<FeedComment>();
		int commentTotalNum = feedDao.commentTotalNum(userFeedNo);
		for(int i = 0; i < commentTotalNum; i++) {
			FeedComment fc = feedDao.selectFeedComment(userFeedNo, i);
			//피드 좋아요 여부 
			int feedCommentNo = feedDao.feedCommentNo(userFeedNo, i);
			int commentLike = feedDao.feedCommentLikeNum(feedCommentNo,userNo);
			fc.setIsLike(commentLike);
			feedCommentList.add(fc);
		}
		feed.setFeedComment(feedCommentList);
		return feed;
	}
	
	public Feed selectUserOneFeed(int userFeedNo) {
		//피드에 있는 사진 가져오기 
		//게시물 정보 가져오기 
		Feed feed = feedDao.getFeed(userFeedNo);
		List<FeedFile> fileList = new ArrayList<FeedFile>();
		for(int i = 0; i < 3; i++) { //user_feed_file_no, user_feed_no, user_feed_filepath
			FeedFile file = feedDao.selectFeedFile(userFeedNo, i+1);
			fileList.add(file);
		}
		
		// 게시물당 사진 게시물 몇개인지
		//int totalImgNo = feedDao.totalImg(userFeedNo);
		// System.out.println(totalImgNo);
		// 게시물 갯수만큼 배열만큼 사진저장
		//List<FeedFile> feedList = new ArrayList<FeedFile>();
		//for (int i = 0; i < totalImgNo; i++) {
		//	String filepath = feedDao.searchFeedImg(userFeedNo, i);
		//	FeedFile fF = new FeedFile();
		//	fF.setUserFeedFilepath(filepath);
		//	feedList.add(fF);
		//}
		//feed.setFeedList(feedList);
		feed.setFeedList(fileList);
		return feed;
	}

	
	@Transactional
	public int deleteFeed(int userFeedNo) {
		int result = feedDao.deleteFeed(userFeedNo);
		return result;
	}

	@Transactional
	public int updatefile(Feed f, List<FeedFile> newFileList, List<FeedFile> fileList) {
		int result = 0;
		result = feedDao.updateFeed(f);
		if (result > 0) {
			if (newFileList.size() == fileList.size()) {
				for (int i = 0; i < newFileList.size(); i++) {
					// 새로운 파일
					String newFilepath = newFileList.get(i).getUserFeedFilepath();
					// 기존 파일
					String originFilepath = fileList.get(i).getUserFeedFilepath();
					//result = feedDao.updateFilePathSame(newFilepath, originFilepath);
				} // for문 끝
			} else if (newFileList.size() > fileList.size()) { // (0 1 2) 3 > (0) 1
				int num = newFileList.size() - fileList.size();// 새로 업데이트 사진이 많으면 //3 - 1 = 2
				// 기존 파일만큼 돌림
				for (int i = 0; i < fileList.size(); i++) { // 1 번 돌림
					// 새로운 파일
					String newFilepath = newFileList.get(i).getUserFeedFilepath(); // 0 , (1,2) 남음
					// 기존 파일
					String originFilepath = fileList.get(i).getUserFeedFilepath();
					//result = feedDao.updateFilePathSame(newFilepath, originFilepath);
				}
				// 새로운 파일 인설트
				for (int i = 0; i < num; i++) { // num == 2
					int j = fileList.size(); // 사이즈 1
					String newFilepath = newFileList.get(j++).getUserFeedFilepath(); // 1, 2
					result = feedDao.updateFeedInsert(newFilepath, f.getUserFeedNo());
				} // for()
			} else if (fileList.size() > newFileList.size()) { // (0 1 2) 3 > (0) 1
				int num = fileList.size() - newFileList.size();// 새로 업데이트 할 사진이 더 적다
				// 적은 파일 만큼 먼저 수정
				for (int i = 0; i < newFileList.size(); i++) {
					// 새로운 파일
					String newFilepath = newFileList.get(i).getUserFeedFilepath();
					// 기존 파일
					String originFilepath = fileList.get(i).getUserFeedFilepath();
					//result = feedDao.updateFilePathSame(newFilepath, originFilepath);
				}
				// 기존 사진 null 로 수정
				for (int i = 0; i < num; i++) { // num == 2
					int j = newFileList.size(); // 사이즈 1
					String originFilepath = fileList.get(j++).getUserFeedFilepath();
					//result = feedDao.updateFeedAnotherNo(originFilepath, f.getUserFeedNo());
				}
			} // else if()
		} // if()
		return result;
	}
	
	
	
	
	
	@Transactional
	public int updateFeedContent(Feed f) {
		int result = feedDao.updateFeed(f);
		return result;
	}
	//피드 게시물 댓글 업데이트 
	@Transactional
	public FeedComment insertFeedComment(String userId, String feedCommentContent, int feedRef) {
		FeedComment fc = null;
		//댓글 업데이트 
		int result = feedDao.insertFeedComment(userId, feedCommentContent, feedRef);
		//업데이트 성공시 댓글 정보 가져오기(실패시 null반환)
		if(result > 0) {
			//업데이트 된 번호가져오기 
			int commentNo = feedDao.getCommentNo();
			//댓글 단 유저의 정보 
			fc = feedDao.getFeedComment(commentNo, userId);
			
		}
		return fc;
	}

	public Feed following(String userFeedWriter) {
		Feed f = new Feed();
		int following = feedDao.following(userFeedWriter);
		f.setUserFeedCount(following);
		List<User> userList = new ArrayList<User>();
		for(int i = 0; i < following; i++) {
			User user = feedDao.searchFollowingUser(userFeedWriter, i+1);	
			userList.add(user);
		}
		f.setUserList(userList);
		return f;
	}

	public Feed follower(String userFeedWriter) {
		int follower = feedDao.follower(userFeedWriter);
		Feed f = new Feed();
		f.setUserFeedCount(follower);
		List<User> userList = new ArrayList<User>();
		for(int i = 0; i < follower; i++) {
			User user = feedDao.searchFollowerUser(userFeedWriter, i+1);	
			userList.add(user);
		}
		f.setUserList(userList);
		return f;
	}

	public int selectFollowBtn(String userFeedWriter, String loginUserId) {
		int result = feedDao.selectFollowBtn(userFeedWriter, loginUserId);
		return result;
	}

	@Transactional
	public int userFollowCancel(String loginUser, String writerUser) {
		int result = feedDao.userFollowCancel(loginUser, writerUser);
		return result;
	}

	@Transactional
	public int userFollow(String loginUser, String writerUser) {
		int result = feedDao.userFollow(loginUser, writerUser);
		return result;
	}
	
	@Transactional
	public int feedCommentDelete(int feedCommentNo) {
		int result = feedDao.feedCommentDelete(feedCommentNo);
		return result;
	}
	@Transactional
	public int feedCommentUpdate(int feedCommentNo, String updatedContent) {
		int result = feedDao.feedCommentUpdate(feedCommentNo, updatedContent);
		return result;
	}

	@Transactional
	public int reportFeed(int feedNo, int userNo) {
		int result = feedDao.reportFeed(feedNo, userNo);
		if(result > 0) { 
			result = feedDao.updateReportFeed(feedNo);
		}
		return result;
	}

	public int isReport(int userFeedNo, int userNo) {
		int reportCount = feedDao.isReport(userFeedNo, userNo);
		return reportCount;
	}

	public int isLike(int userFeedNo, String userId) {
		int likeCount = feedDao.isLike(userFeedNo, userId);
		return likeCount;
	}

	@Transactional
	public int feedLike(int userFeedNo, String userId) {
		int result = feedDao.feedLike(userFeedNo, userId);
		if(result > 0 ) {
			result = feedDao.updateFeedLike(userFeedNo);
		}
		return result;
	}

	@Transactional
	public int feedLikeCancel(int userFeedNo, String userId) {
		int result = feedDao.feedLikeCancel(userFeedNo, userId);
		if(result > 0) {
			result = feedDao.updateFeeddisLike(userFeedNo);
		}
		return result;
	}
	@Transactional
	public int commentLike(int userFeedNo, int userNo) {
		int result = feedDao.commentLike(userFeedNo, userNo);
		return result;
	}
	@Transactional
	public int feedLikeCancel(int feedCommentNo, int userNo) {
		int result = feedDao.feedLikeCancel(feedCommentNo, userNo);
		return result;
	}

	public int isRepository(int userFeedNo, int userNo) {
		int repoCount = feedDao.isRepository(userFeedNo, userNo);
		return repoCount;
	}

	@Transactional
	public int feedRepoIn(int userFeedNo, int userNo) {
		return feedDao.feedRepoIn(userFeedNo, userNo);
	}
	@Transactional
	public int feedRepoOut(int userFeedNo, int userNo) {
		return feedDao.feedRepoOut(userFeedNo, userNo);
	}

	public List<Feed>  userStorage(int userNo) {
		int storageTotal = feedDao.storageTotal(userNo);
		List<Feed> feedList = new ArrayList<Feed>();
		for(int i = 0; i < storageTotal; i++) {
			int feedNo = feedDao.storageFeedNo(userNo, i+1);
			Feed feed = feedDao.getStorageFeed(feedNo);
			feedList.add(feed);
		}
		return feedList;
	}



	@Transactional
	public int updateFeedFilepath(int file1, String filepath) {
		int result = feedDao.updateFeedFilepath(file1, filepath);
		return result;
	}
	@Transactional
	public int updateFeedFilepathNull(int file) {
		int result = feedDao.updateFileNull(file);
		return result;
	}

	public List<Feed> selectReportFeed() {
		List<Feed> feedList = feedDao.selectReportFeed();
		return feedList;
	}

	public int followerNo(String writerUser) {
		int followerNo = feedDao.followerNo(writerUser);
		return followerNo;
	}

	public Feed storageAll(String userId) {
		//보관함에 넣은 텍스트 가져오기 
		List<TextFeed> textFeedList = new ArrayList<TextFeed>();
		int allTextNum = feedDao.allTextNum(userId);
		for(int i = 0; i < allTextNum; i++) {
			TextFeed textFeed = feedDao.repositoryText(userId, i+1);
			textFeedList.add(textFeed);
		}
		//보관함에 넣은 유저피드 게시물
		List<Feed> feedList = new ArrayList<Feed>();
		int allFeedNum = feedDao.allFeedNum(userId);
		for(int i = 0; i < allFeedNum; i++) {
			Feed feed = feedDao.repositoryFeed(userId, i+1);
			feedList.add(feed);
		}
		
		//보관함에 넣은 사진 게시물 
		List<Photo> photoList = new ArrayList<Photo>();
		int allPhotoNum = feedDao.allPhotoNum(userId);
		for(int i = 0; i < allPhotoNum; i ++) {
			Photo photo = feedDao.repositoryPhoto(userId, i+1);
			photoList.add(photo);
		}
		Feed feed = new Feed();
		feed.setPhotoList(photoList);
		feed.setTextList(textFeedList);
		feed.setUserFeedList(feedList);
		return feed;
	}

	public List<Feed> feedAll(String userId) {
		List<Feed> feedList = new ArrayList<Feed>();
		int allFeedNum = feedDao.allFeedNum(userId);
		for(int i = 0; i < allFeedNum; i++) {
			Feed feed = feedDao.repositoryFeed(userId, i+1);
			
			int totalImgNo = feedDao.totalImg(feed.getUserFeedNo());
			List<FeedFile> filepathList = new ArrayList<FeedFile>();
			for(int j = 0; j < totalImgNo; j ++) {
				FeedFile f = new FeedFile();
				String filepath = feedDao.getFilePath(feed.getUserFeedNo(), j+1);
				f.setUserFeedFilepath(filepath);
				filepathList.add(f);
			}
			feed.setFeedList(filepathList);
//			List<FeedFile> filepath= feedDao.repofeedFilepath(feed.getUserFeedNo());
//			feed.setFeedList(filepath);
			feedList.add(feed);
		}
		return feedList;
	}

	public List<Photo> photoAll(String userId) {
		List<Photo> photoList = new ArrayList<Photo>();
		int allPhotoNum = feedDao.allPhotoNum(userId);
		for(int i = 0; i < allPhotoNum; i ++) {
			Photo photo = feedDao.repositoryPhoto(userId, i+1);
			photoList.add(photo);
		}
		return photoList;
	}

	public List<TextFeed> textAll(String userId) {
		List<TextFeed> textFeedList = new ArrayList<TextFeed>();
		int allTextNum = feedDao.allTextNum(userId);
		for(int i = 0; i < allTextNum; i++) {
			TextFeed textFeed = feedDao.repositoryText(userId, i+1);
			textFeedList.add(textFeed);
		}
		return textFeedList;
	}

	
}
