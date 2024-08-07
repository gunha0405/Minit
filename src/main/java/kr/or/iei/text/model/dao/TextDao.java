package kr.or.iei.text.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedComment;
import kr.or.iei.text.model.dto.TextFeedCommentRowMapper;
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

	public List selectTextFeed() {
		String query = "select * from text_feed order by 1 desc";
		List textFeedList = jdbc.query(query, textFeedRowMapper);
		return textFeedList;
	}

	public int textFeedWrite(String textFeedContent, User user) {
		String query = "insert into text_feed values(text_feed_seq.nextval,?,to_char(sysdate,'yyyy-mm-dd'),?)";
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
	    String query = "select * from text_feed_comment where text_feed_comment_ref = ? order by 1 desc";
	    Object[] params = {textFeedNo};
	    List<TextFeedComment> list= jdbc.query(query, textFeedCommentRowMapper, params);
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
	
	public boolean isLikeExists(int textFeedNo, int userNo) {
	    String query = "SELECT COUNT(*) FROM text_feed_like WHERE text_feed_no = ? AND user_no = ?";
	    Object[] params = { textFeedNo, userNo };
	    int count = jdbc.queryForObject(query,Integer.class ,params);
	    return count > 0;
	}
	
	 public int selectTextFeedLikeStatus(int textFeedNo, int userNo) {
	        String query = "SELECT COUNT(*) FROM text_feed_like WHERE text_feed_no = ? AND user_no = ?";
	        Object[] params = {textFeedNo, userNo};
	        int isLike = jdbc.queryForObject(query,Integer.class ,params);
	        return isLike;
	    }
}
