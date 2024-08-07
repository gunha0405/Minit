package kr.or.iei.text.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TextFeedCommentRowMapper implements RowMapper<TextFeedComment>{

	@Override
	public TextFeedComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		TextFeedComment tfc = new TextFeedComment();
		tfc.setTextFeedCommentContent(rs.getString("text_feed_comment_content"));
		tfc.setTextFeedCommentNo(rs.getInt("text_feed_comment_no"));
		tfc.setTextFeedCommentRef(rs.getInt("text_feed_comment_ref"));
		tfc.setTextFeedCommentRegDate(rs.getString("text_feed_comment_reg_date"));
		tfc.setTextFeedCommentWriter(rs.getString("text_feed_comment_writer"));
		return tfc;
	}
	
}
