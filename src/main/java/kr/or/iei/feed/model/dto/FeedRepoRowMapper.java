package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class FeedRepoRowMapper implements RowMapper<Feed>{

	@Override
	public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feed feed = new Feed();
		feed.setUserFeedNo(rs.getInt("user_feed_no"));
		feed.setUserFeedWriter(rs.getString("user_feed_writer"));
		feed.setUserFeedContent(rs.getString("user_feed_content"));
		feed.setUserFeedFilepath(rs.getString("user_feed_filepath"));
		return feed;
	}
	
}
