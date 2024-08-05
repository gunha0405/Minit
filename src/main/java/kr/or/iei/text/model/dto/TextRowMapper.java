package kr.or.iei.text.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TextRowMapper implements RowMapper<Text> {

	@Override
	public Text mapRow(ResultSet rs, int rowNum) throws SQLException {
		Text t = new Text();
		t.setTextFeedNo(rs.getInt("text_feed_no"));
		t.setTextFeedReadCount(rs.getInt("text_feed_read_count"));
		t.setTextFeedRegDate(rs.getString("text_feed_reg_date"));
		t.setTextFeedWriter(rs.getString("text_feed_writer"));
		t.setTextFeedContent(rs.getString("text_feed_content"));
		return t;
	}

}
