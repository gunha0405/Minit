package kr.or.iei.board.model.dto;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FollowingIdRowMapper implements RowMapper<Board> {

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setUserId(rs.getString("user_id"));
		b.setFollowingId(rs.getString("following_id"));
		//b.setTotalLikes(rs.getInt("total_likes"));
		return b;
	}
	
}
