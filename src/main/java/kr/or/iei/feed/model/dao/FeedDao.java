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
import kr.or.iei.feed.model.dto.FeedRepoFilepath;
import kr.or.iei.feed.model.dto.FeedRepoRowMapper;
import kr.or.iei.feed.model.dto.FeedReportRowMapper;
import kr.or.iei.feed.model.dto.FeedRowMapper;
import kr.or.iei.feed.model.dto.FollowUserRowMapper;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoFeedReportRowMapper;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedRowMapper;
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
	@Autowired
	FollowUserRowMapper followUserFowMapper = new FollowUserRowMapper();
	@Value("${file.root}")
	private String root;
	@Autowired
	FeedCommentRowMapper feedCommentRowMapper = new FeedCommentRowMapper();
	@Autowired
	FeedRepoRowMapper feedRepoRowMapper = new FeedRepoRowMapper();
	@Autowired
	FeedReportRowMapper feedReportRowMapper = new FeedReportRowMapper();
	@Autowired
	TextFeedRowMapper textFeedRowMapper = new TextFeedRowMapper();
	@Autowired
	PhotoRowMapper photoRowMapper = new PhotoRowMapper();
	@Autowired
	FeedRepoFilepath feedRepoFilepath = new FeedRepoFilepath();
	
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
		String query = "insert into user_feed_tbl values(user_feed_tbl_seq.nextval,?,?,to_char(sysdate,'YYYY-MM-DD'),0,0)";
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

	public int selectFeedTotalCount(User u) {
		String query = "select count(*) from user_feed_tbl where user_feed_writer=?";
		Object[] params = {u.getUserId()};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
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
		String query = "select count(*) from user_feed_file where user_feed_no=? and user_feed_filepath is not null";
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


	public int updateFeedInsert(String userFeedFilepath, int userFeedNo) {
		String query = "insert into user_feed_file values(user_feed_file_seq.nextval,?,?)";
		Object[] params = {userFeedNo, userFeedFilepath};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateFileNull(int file) {
		String query ="update user_feed_file set user_feed_filepath=null where USER_FEED_FILE_NO=?";
		Object[] params = {file};
		int result = jdbc.update(query, params);
		return result;
	}

	public int inserFeedNull(int feedNo) {
		String query ="insert into user_feed_file values(user_feed_file_seq.nextval,?,null)";
		Object[] params = {feedNo};
		int result = jdbc.update(query, params);
		return result;
	}


	public int insertFeedComment(String userId, String feedCommentContent, int feedRef) {
		String query ="insert into USER_FEED_COMMENT  values(USER_FEED_COMMENT_SEQ.nextval,?,to_char(sysdate,'YYYY-MM-DD'),?,?)";		
		Object[] params = {feedCommentContent, feedRef, userId};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getCommentNo() {
		String query = "select feed_comment_no from (select rownum as rnum, n. * from (select * from user_feed_comment)n order by feed_comment_no desc) where rnum=1";
		int commentNo = jdbc.queryForObject(query, Integer.class);
		return commentNo;
	}

	public FeedComment getFeedComment(int commentNo, String userId) {
		String query = "select feed_comment_writer,feed_comment_no, feed_comment_date,  feed_comment_content, feed_ref, user_img \n" + 
				"from ((select * from user_feed_comment where feed_comment_writer =? and feed_comment_no=?) \n" + 
				"join user_tbl on (feed_comment_writer = user_id))";
		Object[] params = {userId,commentNo};
		List list = jdbc.query(query, feedCommentRowMapper, params);
		//System.out.println(list);
		return (FeedComment)list.get(0);
	}

	public int commentTotalNum(int userFeedNo) {
		String query = "select count(*) from (select *from user_feed_comment where feed_ref=?)";
		Object[] params = {userFeedNo};
		int commentToalNum = jdbc.queryForObject(query, Integer.class, params);
		return commentToalNum;
	}

	public FeedComment selectFeedComment(int userFeedNo, int i) {
		String query = "select feed_comment_content, feed_comment_date, feed_comment_no, feed_comment_writer, feed_ref, user_img\n" + 
				"from \n" + 
				"(select rownum as rnum, n. * from ((select * from (select *from user_feed_comment where feed_ref=?) order by feed_comment_no)n))\n" + 
				"join user_tbl on (feed_comment_writer = user_id) where rnum = ?";
		Object[] params = {userFeedNo, i+1};
		List list = jdbc.query(query, feedCommentRowMapper, params); 
		return (FeedComment)list.get(0);
	}

	public int following(String userFeedWriter) {
		String query = "select count(*) from follow where user_id = ?";
		Object[] params = {userFeedWriter};
		int following = jdbc.queryForObject(query, Integer.class, params);
		return following;
	}

	public int follower(String userFeedWriter) {
		String query ="select count(*) from follow where following_id = ?";
		Object[] params = {userFeedWriter};
		int follower = jdbc.queryForObject(query, Integer.class, params);
		return follower;
	}

	public User searchFollowingUser(String userFeedWriter, int i) {
		String query ="select user_id, user_img from user_tbl\n" + 
				"where user_id=\n" + 
				"(select following_id from (select rownum as rnum, n. * from(select * from follow where user_id = ?)n) where rnum=?)";
		Object[] params = {userFeedWriter, i};
		List list = jdbc.query(query, followUserFowMapper, params);
		return (User)list.get(0);
	}

	public User searchFollowerUser(String userFeedWriter, int i) {
		String query ="select user_id, user_img from user_tbl\n" + 
				"where user_id=\n" + 
				"(select user_id from (select rownum as rnum, n. * from(select * from follow where following_id = ?)n) where rnum=?)";
		Object[] params = {userFeedWriter, i};
		List list = jdbc.query(query, followUserFowMapper, params);
		return (User)list.get(0);
	}

	public int selectFollowBtn(String userFeedWriter, String loginUserId) {
		String query = "SELECT COUNT(*) FROM FOLLOW WHERE USER_ID=? AND FOLLOWING_ID=?";
		Object[] params = {loginUserId, userFeedWriter};
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}

	public int userFollowCancel(String loginUser, String writerUser) {
		String query = "delete from follow where user_id=? and following_id=?";
		Object[] params = {loginUser, writerUser};
		int result = jdbc.update(query, params);
		return result;
	}

	public int userFollow(String loginUser, String writerUser) {
		String query = "insert into follow values (?, ?)";
		Object[] params = {loginUser, writerUser};
		int result = jdbc.update(query, params);
		return result;
	}

	public int feedCommentDelete(int feedCommentNo) {
		String query = "delete from USER_FEED_COMMENT where FEED_COMMENT_NO=?";
		Object[] params = {feedCommentNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int feedCommentUpdate(int feedCommentNo, String updatedContent) {
		String query = "update user_feed_comment set FEED_COMMENT_CONTENT=? where FEED_COMMENT_NO=?";
		Object[] params = {updatedContent, feedCommentNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int reportFeed(int textFeedNo, int userNo) {
		String query = "insert into USER_FEED_CONTENT_REPORT values(?,?)";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);		
		return result;
	}

	public int isReport(int userFeedNo, int userNo) {
		String query = "select count(*) from user_feed_content_report where user_feed_no =? and user_no=?";
		Object[] params = {userFeedNo, userNo};
		int reportCount = jdbc.queryForObject(query, Integer.class, params);
		return reportCount;
	}

	public int isLike(int userFeedNo, String userId) {
		String query = "select count(*) from USER_FEED_LIKE where user_id =? and FEED_NO=?";
		Object[] params = {userId, userFeedNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}

	public int feedLike(int userFeedNo, String userId) {
		String query ="insert into user_feed_like values(?,?)";
		Object[] params = {userId, userFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int feedLikeCancel(int userFeedNo, String userId) {
		String query = "delete from user_feed_like where user_id =? and feed_no=?";
		Object[] params = {userId, userFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int feedCommentNo(int userFeedNo, int i) {
		String query = "select feed_comment_no\r\n" + 
				"from\r\n" + 
				"(select rownum as rnum, n. * from ((select * from (select *from user_feed_comment where feed_ref=?) order by feed_comment_no)n))\r\n" + 
				"join user_tbl on (feed_comment_writer = user_id) \r\n" + 
				"where rnum = ?";
		Object[] params = {userFeedNo, i+1};
		int commentNo = jdbc.queryForObject(query, Integer.class, params);
		return commentNo;
	}

	public int feedCommentLikeNum(int feedCommentNo, int userNo) {
		String query = "select count(*) from USER_FEED_COMMENT_LIKE where user_no=? and feed_comment_no =?";
		Object[] params = {userNo, feedCommentNo};
		int isLike = jdbc.queryForObject(query, Integer.class, params);
		return isLike;
	}

	public int commentLike(int userFeedNo, int userNo) {
		String query = "insert into USER_FEED_COMMENT_LIKE values (?,?)";
		Object[] params = {userFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int feedLikeCancel(int feedCommentNo, int userNo) {
		String query = "delete from user_feed_comment_like where user_no=? and feed_comment_no=?";
		Object[] params = {userNo, feedCommentNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int isRepository(int userFeedNo, int userNo) {
		String query = "select count(*) from REPOSITORY where REPOSITORY_USER_NO=? and REPOSITORY_FEED_NO=?";
		Object[] params = {userNo, userFeedNo};
		int repoCount = jdbc.queryForObject(query, Integer.class, params);
		return repoCount;
	}

	public int feedRepoIn(int userFeedNo, int userNo) {
		String query = "insert into REPOSITORY values (REPOSITORY_seq.nextval,?,?,null,null)";
		Object[] params = {userNo, userFeedNo};
		return jdbc.update(query, params);
	}

	public int feedRepoOut(int userFeedNo, int userNo) {
		String qeury = "delete from repository where REPOSITORY_USER_NO=? and REPOSITORY_FEED_NO=?";
		Object[] params = {userNo, userFeedNo};
		return jdbc.update(qeury, params);
	}

	public int storageTotal(int userNo) {
		String query = "select count(*) from repository where REPOSITORY_USER_NO=?";
		Object[] params = {userNo};
		return jdbc.queryForObject(query, Integer.class, params);
	}

	public int storageFeedNo(int userNo, int i) {
		String query = "select repository_feed_no from\r\n" + 
				"(select rownum as rnum, n.* from \r\n" + 
				"(select *from repository where REPOSITORY_USER_NO=? order by repository_feed_no)n) where rnum =?";
		Object[] params = {userNo, i};
		return jdbc.queryForObject(query, Integer.class, params);
	}

	public Feed getStorageFeed(int feedNo) {
		String query = "select user_feed_no, user_feed_writer, user_feed_content, user_feed_filepath from\r\n" + 
				"(select rownum as rnum, n.*from\r\n" + 
				"(select\r\n" + 
				"user_feed_no, user_feed_writer, user_feed_content, user_feed_filepath\r\n" + 
				"from\r\n" + 
				"(select * from user_feed_tbl\r\n" + 
				"join user_tbl on (user_feed_writer= user_id)\r\n" + 
				"join user_feed_file using (user_feed_no)\r\n" + 
				"where user_feed_no =?))n)\r\n" + 
				"where rnum =1";
		Object[] params = {feedNo};
		List list = jdbc.query(query, feedRepoRowMapper, params);
		return (Feed)list.get(0);
	}

	public FeedFile selectFeedFile(int userFeedNo, int i) {
		String query = "select user_feed_file_no, user_feed_no, user_feed_filepath from\r\n" + 
				"(select rownum as rnum, n. *from (select *from user_feed_file where user_feed_no = ?)n) where rnum =?";
		Object[] params = {userFeedNo, i};
		List list = jdbc.query(query, feedFileRowMapper, params);		
		return (FeedFile)list.get(0);
	}

	public Feed getFeed(int userFeedNo) {
		String query = "select * from user_feed_tbl where user_feed_no = ?";
		Object[] params = {userFeedNo};
		List list = jdbc.query(query, feedRowMapper, params);
		return (Feed)list.get(0);
	}

	public int updateFeedFilepath(int file, String filepath) {
		String query = "update user_feed_file set USER_FEED_FILEPATH=? where USER_FEED_FILE_NO=?";
		Object[] params = {filepath, file};
		int result = jdbc.update(query, params);
		return result;
	}

	public String getFilePath(int userFeedNo, int i) {
		String query = "select user_feed_filepath from\r\n" + 
				"(select rownum as rnum, n. * from \r\n" + 
				"(select * from user_feed_file where user_feed_no=? and user_feed_filepath is not null)n) where rnum = ?";
		Object[] params = {userFeedNo,i};
		String filePath = jdbc.queryForObject(query,String.class, params);
		//System.out.println("filePath"+filePath);
		return filePath;
	}

	public int updateReportFeed(int feedNo) {
		String query = "update user_feed_tbl set user_feed_count = +1 where user_feed_no=?";
		Object[] params = {feedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public List<Feed> selectReportFeed() {
		String query = "select user_no, user_feed_writer, user_feed_no, user_feed_content, user_feed_date, user_feed_count from user_feed_tbl \r\n" + 
				"join user_tbl on (user_feed_writer = user_id)\r\n" + 
				"where user_feed_count > 0";
		List<Feed> feedlist = jdbc.query(query, feedReportRowMapper);
		System.out.println(feedlist);
		return feedlist;
	}

	public int followerNo(String writerUser) {
		String query = "select count(*) from follow where following_id=?";
		Object[] params = {writerUser};
		int followerNo = jdbc.queryForObject(query, Integer.class,params);
		return followerNo;
	}

	public String totlaImg(int userFeedNo) {
		String query = "select user_img from user_tbl \r\n" + 
				"join user_feed_tbl on (user_id = user_feed_writer) where user_feed_no=?";
		Object[] params = {userFeedNo};
		String userImg = jdbc.queryForObject(query, String.class, params);
		return userImg;
	}

	public int updateFeedLike(int userFeedNo) {
		String query ="update user_feed_tbl set feed_like_count=+1 where user_feed_no=?";
		Object[] params = {userFeedNo};
		return jdbc.update(query, params);
	}

	public int updateFeeddisLike(int userFeedNo) {
		String query ="update user_feed_tbl set feed_like_count=-1 where user_feed_no=?";
		Object[] params = {userFeedNo};
		return jdbc.update(query, params);
	}

	public int allTextNum(String userId) {
		String query = "select count(*) from (select repository_text_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_text_feed_no is not null)";
		Object[] params = {userId};
		return jdbc.queryForObject(query, Integer.class,params);
	}

	public TextFeed repositoryText(String userId, int i) {
		String query ="select text_feed_no, text_feed_reg_date, text_feed_writer, text_feed_content\n" + 
				"from text_feed\n" + 
				"where text_feed_no=\n" + 
				"(select repository_text_feed_no from\n" + 
				"(select rownum as rnum, n. * from(\n" + 
				"select repository_text_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_text_feed_no is not null order by repository_text_feed_no)n) \n" + 
				"where rnum=?)";
		Object[] params = {userId, i};
		List list = jdbc.query(query, textFeedRowMapper, params);
		return (TextFeed)list.get(0);
	}

	public int allPhotoNum(String userId) {
		String query = "select count(*) from (select repository_photo_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_photo_feed_no is not null)";
		Object[] params = {userId};
		return jdbc.queryForObject(query, Integer.class,params);

	}

	public Photo repositoryPhoto(String userId, int i) {
		String query = "select photo_feed_no, photo_feed_img, photo_feed_writer, read_count, reg_date\n" + 
				"from photo_feed\n" + 
				"where photo_feed_no=\n" + 
				"(select repository_photo_feed_no from\n" + 
				"(select rownum as rnum, n. * from(\n" + 
				"select repository_photo_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_photo_feed_no is not null order by repository_photo_feed_no)n) \n" + 
				"where rnum=?)";
		Object[] params = {userId, i};
		List list = jdbc.query(query, photoRowMapper, params);
		return (Photo)list.get(0);
	}

	public int allFeedNum(String userId) {
		String query ="select count(*) from (select repository_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_feed_no is not null)";
		Object[] params = {userId};
		return jdbc.queryForObject(query, Integer.class,params);
	}

	public Feed repositoryFeed(String userId, int i) {
		String query = "select user_feed_no, user_feed_writer, user_feed_content, user_feed_filepath \n" + 
				"from \n" + 
				"(select rownum as rnum, n. * from \n" + 
				"(select * from user_feed_tbl\n" + 
				"join user_feed_file using(user_Feed_no) where \n" + 
				"user_feed_filepath is not null\n" + 
				"and user_feed_no =\n" + 
				"(\n" + 
				"select repository_feed_no from repository \n" + 
				"where repository_feed_no =\n" + 
				"(select repository_feed_no from\n" + 
				"(select rownum as rnum, n. * from \n" + 
				"(select * from (select repository_feed_no from repository  where repository_user_no\n" + 
				"= (select user_no from user_tbl where user_id=?) and repository_feed_no is not null))n) where rnum =?)\n" + 
				")\n" + 
				")n) where rnum = 1\n";
		Object[] params = {userId, i};
		List list = jdbc.query(query, feedRepoRowMapper, params);
		return (Feed)list.get(0);
	}

//	public List<FeedFile> repofeedFilepath(int userFeedNo) {
//		String query = "select user_feed_filepath from user_feed_file where user_feed_filepath is not null and user_feed_no=?";
//		Object[] params = {userFeedNo};
//		List list = jdbc.query(query, feedRepoFilepath, params);
//		return (List<FeedFile>)list;
//	}


}
