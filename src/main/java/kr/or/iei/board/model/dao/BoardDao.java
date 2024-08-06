package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.text.model.dto.TextFeed;

@Repository
public class BoardDao {
	@Autowired
	JdbcTemplate jdbc = new JdbcTemplate();
	@Autowired
	BoardRowMapper boardRowMapper = new BoardRowMapper();
	public List AllTextFeeds() {
		String query = "select * from text_feed";
		List list = jdbc.query(query, boardRowMapper);
		return list;
	}
	/*
	public List allTextfeed() {
		String query = "select * from text_feed";
		List list = jdbc.query(query, boardRowMapper);
		return list;
	}
	*/
	

}



