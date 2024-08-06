package kr.or.iei.text.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TextFeedRowMapper implements RowMapper<TextFeed> {

	@Override
	public TextFeed mapRow(ResultSet rs, int rowNum) throws SQLException {
		TextFeed t = new TextFeed();
		t.setTextFeedNo(rs.getInt("text_feed_no"));
		t.setTextFeedRegDate(rs.getString("text_feed_reg_date"));
		t.setTextFeedWriter(rs.getString("text_feed_writer"));
		t.setTextFeedContent(rs.getString("text_feed_content"));
		return t;
	}

}
