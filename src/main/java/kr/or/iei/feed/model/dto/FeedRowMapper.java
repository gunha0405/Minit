package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FeedRowMapper implements RowMapper<Feed>{

	@Override
	public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feed feed = new Feed();
		feed.setUserFeedNo(rs.getInt("user_feed_no"));
		feed.setUserFeedWriter(rs.getString("user_feed_writer"));
		feed.setUserFeedContent(rs.getString("user_feed_content"));
		feed.setUserFeedDate(rs.getString("user_feed_date"));
		feed.setUserFeedCount(rs.getInt("user_feed_count"));
		feed.setFeedLikeCount(rs.getInt("feed_like_count"));
		//feed.setUserFeedFilepath(rs.getNString("user_feed_filepath"));
		return feed;
	}

}
