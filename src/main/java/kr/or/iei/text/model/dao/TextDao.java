package kr.or.iei.text.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedComment;
import kr.or.iei.text.model.dto.TextFeedCommentPhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeedCommentRowMapper;
import kr.or.iei.text.model.dto.TextFeedPhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeedReportRowMapper;
import kr.or.iei.text.model.dto.TextFeedRowMapper;
import kr.or.iei.user.model.dto.User;

@Repository
public class TextDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private TextFeedRowMapper textFeedRowMapper;
	
	@Autowired
	private TextFeedCommentRowMapper textFeedCommentRowMapper;
	
	@Autowired
	private TextFeedReportRowMapper textFeedReportRowMapper;
	
	@Autowired
	private TextFeedPhotoRowMapper textFeedPhotoRowMapper;
	
	@Autowired
	private TextFeedCommentPhotoRowMapper textFeedCommentPhotoRowMapper;
	

	public List selectTextFeed() {
		String query = "select * from text_feed join user_tbl on text_feed_writer = user_id order by 1 desc";
		List textFeedList = jdbc.query(query, textFeedPhotoRowMapper);
		return textFeedList;
	}

	public int textFeedWrite(String textFeedContent, User user) {
		String query = "insert into text_feed values(text_feed_seq.nextval,?,to_char(sysdate,'yyyy-mm-dd'),?,0)";
		Object[] params = {textFeedContent, user.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}

	

	public int getTextFeedNo() {
		String query = "select max(text_feed_no) from text_feed";
		int textFeedNo = jdbc.queryForObject(query, Integer.class);
		return textFeedNo;
	}

	public TextFeed selectOneTextFeed(int textFeedNo) {
		String query = "select * from text_feed where text_feed_no = ?";
		Object[] params = {textFeedNo};
		List list = jdbc.query(query,textFeedRowMapper ,params);
		return (TextFeed)list.get(0);
	}

	public int deleteTextFeed(int textFeedNo) {
		String query = "delete from text_feed where text_feed_no = ?";
		Object[] params = {textFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int textFeedCommentWrite(String textFeedCommentContent,int textFeedNo, User user) {
		String query = "insert into text_feed_comment values(text_feed_comment_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object[] params = {textFeedCommentContent, user.getUserId(), textFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getTextFeedCommentNo() {
		String query = "select max(text_feed_comment_no) from text_feed_comment";
		int textFeedNo = jdbc.queryForObject(query, Integer.class);
		return textFeedNo;
	}
	
	public TextFeedComment selectOneTextFeedComment(int textFeedCommentNo) {
		String query = "select * from text_feed_comment where text_feed_comment_no = ?";
		Object[] params = {textFeedCommentNo};
		List list = jdbc.query(query, textFeedCommentRowMapper, params);
		return (TextFeedComment)list.get(0);
	}

	

	public List<TextFeedComment> selectTextFeedComment(int textFeedNo) {
	    String query = "select * from text_feed_comment join user_tbl on text_feed_comment_writer = user_id where text_feed_comment_ref = ?";
	    Object[] params = {textFeedNo};
	    List<TextFeedComment> list= jdbc.query(query, textFeedCommentPhotoRowMapper, params);
	    return list;
	}

	public int deleteTextFeedComment(int textFeedCommentNo) {
		String query = "delete from text_feed_comment where text_feed_comment_no = ?";
		Object[] params = {textFeedCommentNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int editTextFeed(String textFeedEditContent, int textFeedEditNo) {
		String query = "update text_feed set text_feed_content = ? where text_feed_no = ?";
		Object[] params = {textFeedEditContent, textFeedEditNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int editTextFeedComment(String textFeedCommentEditContent, int textFeedCommentEditNo) {
		String query = "update text_feed_comment set text_feed_comment_content = ? where text_feed_comment_no = ?";
		Object[] params = {textFeedCommentEditContent, textFeedCommentEditNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int insertTextFeedLike(int textFeedNo, int userNo) {
		String query = "insert into text_feed_like values(?,?)";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteTextFeedLike(int textFeedNo, int userNo) {
		String query = "delete from text_feed_like where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public boolean isLikeFeedExists(int textFeedNo, int userNo) {
	    String query = "select count(*) from text_feed_like where text_feed_no = ? and user_no = ?";
	    Object[] params = { textFeedNo, userNo };
	    int count = jdbc.queryForObject(query,Integer.class ,params);
	    return count > 0;
	}
	
	public int selectTextFeedLikeStatus(int textFeedNo, int userNo) {
	    String query = "select count(*) from text_feed_like where text_feed_no = ? and user_no = ?";
	    Object[] params = {textFeedNo, userNo};
	    int isLike = jdbc.queryForObject(query,Integer.class ,params);
	    return isLike;
	}
	
	public int insertTextFeedCommentLike(int textFeedCommentNo, int userNo) {
		String query = "insert into text_feed_comment_like values(?,?)";
		Object[] params = {textFeedCommentNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int deleteTextFeedCommentLike(int textFeedCommentNo, int userNo) {
		String query = "delete from text_feed_comment_like where text_feed_comment_no = ? and user_no = ?";
		Object[] params = {textFeedCommentNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public boolean isLikeCommentExists(int textFeedCommentNo, int userNo) {
		String query = "select count(*) from text_feed_comment_like where text_feed_comment_no = ? and user_no = ?";
		Object[] params = {textFeedCommentNo, userNo};
		int count = jdbc.queryForObject(query, Integer.class, params);
		return count>0;
	}

	public int selectTextFeedCommentLikeStatus(int textFeedCommentNo, int userNo) {
		String query = "select count(*) from text_feed_comment_like where text_feed_comment_no = ? and user_no = ?";
	    Object[] params = {textFeedCommentNo, userNo};
	    int isLike = jdbc.queryForObject(query,Integer.class ,params);
	    return isLike;
	}
	
	public boolean isReportFeedExists(int textFeedNo, int userNo) {
		String query = "select count(*) from text_feed_report where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int count = jdbc.queryForObject(query,Integer.class, params);
		return count>0;
	}
	
	public int insertTextFeedReport(int textFeedNo, int userNo) {
		String query = "insert into text_feed_report values(?,?)";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectTextFeedReportStatus(int textFeedNo, int userNo) {
		String query = "select count(*) from text_feed_report where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int isReport = jdbc.queryForObject(query, Integer.class, params);
		return isReport;
	}

	public int selectTextFeedReportCount(int textFeedNo) {
		String query = "select count(*) from text_feed_report where text_feed_no = ?";
		Object[] params = {textFeedNo};
		int reportCount = jdbc.queryForObject(query, Integer.class, params);
		return reportCount;
	}

	

	public int hideTextFeed(int textFeedNo) {
		String query = "update text_feed set text_feed_status = 1 where text_feed_no = ?";
		Object[] params = {textFeedNo};
		int hideTextFeedResult = jdbc.update(query, params);
		return hideTextFeedResult;
	}

	public boolean isSaveFeedExists(int textFeedNo, int userNo) {
		String query = "select count(*) from text_feed_save where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int count = jdbc.queryForObject(query,Integer.class ,params);
		return count>0;
	}

	public int insertTextFeedSave(int textFeedNo, int userNo) {
		String query = "insert into text_feed_save values(?,?)";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteTextFeedSave(int textFeedNo, int userNo) {
		String query = "delete from text_feed_save where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectTextFeedSaveStatus(int textFeedNo, int userNo) {
		String query = "select count(*) from text_feed_save where text_feed_no = ? and user_no = ?";
		Object[] params = {textFeedNo, userNo};
		int isSave = jdbc.queryForObject(query, Integer.class, params);
		return isSave;
	}

	public List<TextFeed> selectReportFeed() {
		String query = "select text_feed_content, text_feed_no, user_no, text_feed_writer, warning_count,text_feed_reg_date from text_feed join user_tbl on text_feed_writer = user_id where text_feed_status = 1";
		List<TextFeed> reportList = jdbc.query(query, textFeedReportRowMapper);
		return reportList;
	}

	public int countLike(int textFeedNo) {
		String query = "select count(*) from text_feed_like where text_feed_no = ?";
		Object[] params = {textFeedNo};
		int likeCount = jdbc.queryForObject(query, Integer.class , params);
		return likeCount;
	}
 
	public int countCommentLike(int textFeedCommentNo) {
		String query = "select count(*) from text_feed_comment_like where text_feed_comment_no = ?";
		Object[] params = {textFeedCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
	

	
}
