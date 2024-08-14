package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class FeedReportRowMapper implements RowMapper<Feed>{

	@Override
	public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feed f = new Feed();
		f.setUserNo(rs.getInt("user_no"));
		f.setUserFeedNo(rs.getInt("user_feed_no"));
		f.setUserFeedWriter(rs.getString("user_feed_writer"));
		f.setUserFeedContent(rs.getString("user_feed_content"));
		f.setUserFeedDate(rs.getString("user_feed_date"));
		f.setUserFeedCount(rs.getInt("user_feed_count"));
		return f;
	}

}
