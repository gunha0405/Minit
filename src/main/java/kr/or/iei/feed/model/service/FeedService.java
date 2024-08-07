package kr.or.iei.feed.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.feed.model.dao.FeedDao;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.user.model.dto.User;

@Service
public class FeedService {
	@Autowired
	private FeedDao feedDao;

	public UserFeedNaviList feedList(String userId) {
		List<Feed> feedList = feedDao.feedList(userId);
		if(feedList == null) {
			User user = feedDao.userInfo(userId);
			UserFeedNaviList userFeedList = new UserFeedNaviList();
			userFeedList.setUser(user);
			return userFeedList;			
		}else {
			User user = feedDao.userInfo(userId);
			UserFeedNaviList userFeedList = new UserFeedNaviList(user, feedList);
			return userFeedList;	
		}
	}

	public User searchUser(String userId) {
		System.out.println("userId" + userId);
		User u = feedDao.searchUser(userId);
		return u;
	}

	public int insertfile(Feed f, List<FeedFile> fileList) {
		 int result = feedDao.insertFeed(f);
	        if(result >0) {
	            //1번작업으로 insert할때 생성된 번호를 조회
	            int feedNo = feedDao.selectFeedNo();
	            //3. 반복문으로 feed_file 테이블 insert
	            for(FeedFile feedFile : fileList) {
	                //1번작업으로 insert될때 생성된 공지사항번호를 저장한 후 feed_file insert 요청
	            	feedFile.setUserFeedNo(feedNo);
	                //file_no = 0, notice_no = noticeNo, filename,filepath
	                result += feedDao.insertFeedFile(feedFile);
	            }
	        }
	        return result;
	}

}
