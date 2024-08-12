package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FeedFileRowMapper implements RowMapper<FeedFile>{

	@Override
	public FeedFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeedFile f = new FeedFile();
		f.setUserFeedFileNo(rs.getInt("user_feed_file_no"));
		f.setUserFeedFilepath(rs.getString("user_feed_filepath"));
		f.setUserFeedNo(rs.getInt("user_feed_no"));
		return f;
	}

}