package kr.or.iei.feed.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.FeedRowMapper;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;

@Repository
public class FeedDao {
	@Autowired
	JdbcTemplate jdbc = new JdbcTemplate();
	@Autowired
	FeedRowMapper feedRowMapper = new FeedRowMapper();
	@Autowired
	UserRowMapper userRowMapper = new UserRowMapper();
	
	public UserFeedNaviList userList(String userId) {
		String query = "select user_id, user_info, USER_FEED_WRITER, USER_FEED_CONTENT,USER_FEED_DATE\r\n" + 
				"from user_tbl\r\n" + 
				"join user_feed_tbl on (user_id=user_feed_writer) \r\n" + 
				"where user_feed_writer=?";
		Object[] params = {userId};
		UserFeedNaviList userFeedList = (UserFeedNaviList)jdbc.query(query, feedRowMapper, params);
		return userFeedList;
	}
	
	public List<Feed> feedList(String userId) {
		String query = "USER_FEED_NO, USER_FEED_WRITER, USER_FEED_CONTENT,USER_FEED_DATE,USER_FEED_COUNT\r\n" + 
				"from user_tbl\r\n" + 
				"join user_feed_tbl on (user_id=user_feed_writer) \r\n" + 
				"where user_feed_writer=?";
		Object[] params = {userId};
		List<Feed> feedList = (List<Feed>)jdbc.query(query, feedRowMapper, params);
		return feedList;
	}

	public User userInfo(String userId) {
		String query = "select * from user_tbl where user_id = ?";
		Object[] params = {userId};
		User user = (User)jdbc.query(query, userRowMapper, params);
		return user;
	}

	public User searchUser(String userId) {
		String query = "select * from user_tbl where user_id = ?";
		Object[] params = {userId};
		List list = jdbc.query(query, userRowMapper, params);
		return (User)list.get(0);
	}

	public int insertFeed(Feed f) {
		String query = "insert into user_feed_tbl values(user_feed_tbl_seq.nextval,?,?,to_char(sysdate,'YYYY-MM-DD'),0)";
		Object[] params = {f.getUserFeedWriter(), f.getUserFeedContnet()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectFeedNo() {
		String query = "select max(user_feed_no) from user_feed_tbl";
		int feedNo = jdbc.queryForObject(query, Integer.class);
		return feedNo;
	}

	public int insertFeedFile(FeedFile feedFile) {
		String query = "insert into user_feed_file values(user_feed_file_seq.nextval,?,?)";
		Object[] params = {feedFile.getUserFeedNo(),feedFile.getUserFeedFilpath()};
		int result = jdbc.update(query, params);
		return result;
	}
}
