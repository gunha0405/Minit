package kr.or.iei.text.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedRowMapper;
import kr.or.iei.user.model.dto.User;

@Repository
public class TextDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private TextFeedRowMapper textFeedRowMapper;

	public List selectTextFeed() {
		String query = "select * from text_feed order by 1 desc";
		List list = jdbc.query(query, textFeedRowMapper);
		return list;
	}

	public int insertTextFeed(String textFeedContent, User user) {
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
}
