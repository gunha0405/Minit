package kr.or.iei.text.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextFeedRowMapper;

@Repository
public class TextDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private TextFeedRowMapper textFeedRowMapper;

	public List selectTextFeed() {
		String query = "select * from text_feed order by 1";
		List list = jdbc.query(query, textFeedRowMapper);
		return list;
	}
}
