package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class FeedRepoFilepath implements RowMapper<FeedFile> {

	@Override
	public FeedFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeedFile f = new FeedFile();
		f.setUserFeedFilepath(rs.getString("user_feed_filepath"));
		return null;
	}

}
