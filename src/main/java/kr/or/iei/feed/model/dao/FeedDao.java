package kr.or.iei.feed.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedComment;
import kr.or.iei.feed.model.dto.FeedCommentRowMapper;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.FeedFileRowMapper;
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
	FeedFileRowMapper feedFileRowMapper = new FeedFileRowMapper();
	@Autowired
	UserRowMapper userRowMapper = new UserRowMapper();
	@Value("${file.root}")
	private String root;
	@Autowired
	FeedCommentRowMapper feedCommentRowMapper = new FeedCommentRowMapper();
	
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
	
	public User searchUser(int userId) {
		String query = "select * from user_tbl where user_id = ?";
		Object[] params = {userId};
		List list = jdbc.query(query, userRowMapper, params);
		return (User)list.get(0);
	}

	public int insertFeed(Feed f) {
		String query = "insert into user_feed_tbl values(user_feed_tbl_seq.nextval,?,?,to_char(sysdate,'YYYY-MM-DD'),0)";
		Object[] params = {f.getUserFeedWriter(), f.getUserFeedContent()};
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
		Object[] params = {feedFile.getUserFeedNo(),feedFile.getUserFeedFilepath()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectFeedList(int start, int end, User u) {
		String userId = u.getUserId();
		String query = "select count(*)from (select user_feed_no \r\n" + 
				"from (select rownum as rnum, n.*from(select user_feed_no from user_feed_tbl join user_feed_file using(user_feed_no) \r\n" + 
				"where USER_FEED_WRITER = ? group by user_feed_no order by 1)n) \r\n" + 
				"where rnum between ? and ?)";
        Object[] params = {u.getUserId(),start,end};
        int totalCount = jdbc.queryForObject(query, Integer.class,params);
        return totalCount;
	}

	public int selectFeedTotalCount() {
		String query = "select count(*) from user_feed_tbl";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public int searchUserFeedNum(String userId) {
		String query = "select count(*) from (select user_feed_no from user_feed_tbl join user_feed_file using(user_feed_no) where USER_FEED_WRITER = ? group by user_feed_no)"; 
		Object[] parmas = {userId};
		int totalCount = jdbc.queryForObject(query, Integer.class, parmas);
		return totalCount;
	}

	public int searhFeedNo(int start, int end, User u, int i) {
		String query = "select user_feed_no from\r\n" + 
				"(select rownum as rnum, \r\n" + 
				"n.*from(select user_feed_no from (select rownum as rnum, \r\n" + 
				"n.*from(select user_feed_no from user_feed_tbl join user_feed_file using(user_feed_no) \r\n" + 
				"where USER_FEED_WRITER = ? and user_feed_filepath is not null group by user_feed_no order by 1)n)\r\n" + 
				"where rnum between ? and ?)n) where rnum=?";
		Object[] params = {u.getUserId(),start,end, i};
		int feedNo = jdbc.queryForObject(query, Integer.class, params);
		return feedNo;
	}

	public String feedFilepath(int start, int end, User u, int feedNo) {
		String query = "select user_feed_filepath from (select rownum as rnum, n.*from(select * from user_feed_tbl join user_feed_file using(user_feed_no) where user_feed_no=? and user_feed_filepath is not null)n) where rownum=1";
		Object[] params = {feedNo};
		String file = jdbc.queryForObject(query, String.class, params);
		return file;
	}

	public Feed searchFeedUser(int userFeedNo) {
		String query = "select * from user_feed_tbl where user_feed_no = ?";
		Object[] params = {userFeedNo};
		List list = jdbc.query(query, feedRowMapper, params);
		return (Feed)list.get(0);
	}

	public int totalImg(int userFeedNo) {
		String query = "select count(*) from user_feed_file where user_feed_no=?";
		Object[] params = {userFeedNo};
		int totalImgNo = jdbc.queryForObject(query, Integer.class, params);
		return totalImgNo;
	}

	public String searchFeedImg(int userFeedNo, int i) {
		String query = "select user_feed_filepath from (select rownum rnum, n.*from(select user_feed_filepath from user_feed_file where user_feed_no = ? )n) where rnum = ?";
		Object[] params = {userFeedNo,i+1};
		String file = jdbc.queryForObject(query, String.class, params);
		String fath = "/feed/";
		String filefath = fath+file;
		return filefath;
	}

	public int deleteFeed(int userFeedNo) {
		String query = "delete from user_feed_tbl where user_feed_no =?";
		Object[] params = {userFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateFeed(Feed f) {
		String query = "update user_feed_tbl set user_feed_content=? where user_feed_no=?";
		Object[] params = {f.getUserFeedContent(),f.getUserFeedNo()};
		int result = jdbc.update(query, params);
		return result;
	}


	public int updateFilePathSame(String string, int fileNo) {
		String query = "update user_feed_file set user_feed_filepath=? where user_feed_file_no=?";
		Object[] params = {string, fileNo};
		int result = jdbc.update(query, params);
		return result;
		
	}


	public int updateFeedInsert(String userFeedFilepath, int userFeedNo) {
		String query = "insert into user_feed_file values(user_feed_file_seq.nextval,?,?)";
		Object[] params = {userFeedNo, userFeedFilepath};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateFileNull(int userFeedNo) {
		String query ="update user_feed_file set user_feed_filepath=null where user_feed_no=?";
		Object[] params = {userFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int inserFeedNull(int feedNo) {
		String query ="insert into user_feed_file values(user_feed_file_seq.nextval,?,null)";
		Object[] params = {feedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getFileNo(int userFeedNo, int i) {
		String query="select user_feed_file_no  from (select rownum as rnum , n. * from ((select user_feed_file_no, user_feed_filepath from  user_feed_file where user_feed_no=?)n)) where rnum=?";
		Object[] params = {userFeedNo, i+1};
		int fileNo = jdbc.queryForObject(query, Integer.class, params);
		return fileNo;
	}

	public int insertFeedComment(String userId, String feedCommentContent, int feedRef) {
		String query ="insert into USER_FEED_COMMENT  values(USER_FEED_COMMENT_SEQ.nextval,?,to_char(sysdate,'YYYY-MM-DD'),?,?)";
		Object[] params = {feedCommentContent, feedRef, userId};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getCommentNo() {
		String query = "select count(*) from user_feed_comment";
		int commentNo = jdbc.queryForObject(query, Integer.class);
		return commentNo;
	}

	public FeedComment getFeedComment(int commentNo, String userId) {
		String query = "select \r\n" + 
				"user_id, user_img,feed_comment_no, feed_comment_content, feed_comment_date, feed_ref \r\n" + 
				"from\r\n" + 
				"(select * from user_tbl join USER_FEED_COMMENT on (user_id = FEED_COMMENT_WRITER)) \r\n" + 
				"where feed_comment_no=? and user_id=?";
		Object[] params = {commentNo, userId};
		List list = jdbc.query(query, feedCommentRowMapper, params);
		System.out.println(list);
		return (FeedComment)list.get(0);
	}


}
