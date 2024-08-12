package kr.or.iei.board.model.dto;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.or.iei.feed.model.dto.Feed;

@Component
public class AllFeedRowMapper implements RowMapper<Feed> {

	@Override
	public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feed f = new Feed();
		f.setUserFeedFilepath(rs.getString("user_feed_filepath"));
		f.setUserFeedWriter(rs.getString("user_feed_writer"));
		f.setUserFeedContent(rs.getString("user_feed_content"));
		f.setUserFeedDate(rs.getString("user_feed_date"));
		return f;
	}
	
	
}

