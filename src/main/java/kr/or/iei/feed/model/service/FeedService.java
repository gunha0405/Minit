package kr.or.iei.feed.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.feed.model.dao.FeedDao;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.UserFeedList;
import kr.or.iei.user.model.dto.User;

@Service
public class FeedService {
	@Autowired
	private FeedDao feedDao;

	public UserFeedList feedList(String userId) {
		List<Feed> feedList = feedDao.feedList(userId);
		if(feedList == null) {
			User user = feedDao.userInfo(userId);
			UserFeedList userFeedList = new UserFeedList();
			userFeedList.setUser(user);
			return userFeedList;			
		}else {
			User user = feedDao.userInfo(userId);
			UserFeedList userFeedList = new UserFeedList(user, feedList);
			return userFeedList;	
		}
	}

	public User searchUser(String userId) {
		System.out.println("userId" + userId);
		User u = feedDao.searchUser(userId);
		return u;
	}

}
