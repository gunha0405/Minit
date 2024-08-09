package kr.or.iei.text.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TextFeedReportRowMapper implements RowMapper<TextFeed>{

	@Override
	public TextFeed mapRow(ResultSet rs, int rowNum) throws SQLException {
		TextFeed tf = new TextFeed();
		tf.setTextFeedContent(rs.getString("text_feed_content"));
		tf.setTextFeedNo(rs.getInt("text_feed_no"));
		tf.setTextFeedWriter(rs.getString("text_feed_writer"));
		tf.setUserNo(rs.getInt("user_no"));
		tf.setWarningCount(rs.getInt("warning_count"));
		tf.setTextFeedRegDate(rs.getString("text_feed_reg_date"));
		return tf;
	}
	
}
