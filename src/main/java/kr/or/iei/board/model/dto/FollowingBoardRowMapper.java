package kr.or.iei.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FollowingBoardRowMapper implements RowMapper<Board> {

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setUserFeedNo(rs.getInt("user_feed_No"));
		b.setUserFeedWriter(rs.getString("user_feed_writer"));
		b.setUserFeedContnet(rs.getString("user_feed_date"));
		b.setUserFeedDate(rs.getString("user_feed_date"));
		b.setUserFeedCount(rs.getInt("user_feed_count"));
		return b;
	}

}
