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
		List list = jdbc.query(query, textFeedRowMapper);
		return list;
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
	    String query = "select * from text_feed_comment where text_feed_comment_ref = ?";
	    Object[] params = {textFeedNo};
	    List<TextFeedComment> list= jdbc.query(query, textFeedCommentRowMapper, params);
	    return list;
	}
}
