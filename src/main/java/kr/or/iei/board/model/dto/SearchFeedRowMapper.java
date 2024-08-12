package kr.or.iei.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SearchFeedRowMapper implements RowMapper<Board>{

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setUserFeedWriter(rs.getString("user_feed_writer"));
		b.setUserFeedDate(rs.getString("user_feed_date"));	
		b.setUserFeedContnet(rs.getString("user_feed_content"));
		b.setUserFeedNo(rs.getInt("user_feed_file_no"));
		b.setUserFeedFilePath(rs.getString("user_feed_filepath"));
		return b;
	}
	

}
