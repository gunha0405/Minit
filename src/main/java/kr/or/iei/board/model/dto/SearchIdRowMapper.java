package kr.or.iei.board.model.dto;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SearchIdRowMapper implements RowMapper<Board> {

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setUserNick(rs.getString("user_nick"));
		b.setPhotoFeedImg(rs.getString("photo_feed_img"));
		b.setRegDate("reg_date");
		b.setTotalLikes(rs.getInt("total_likes"));
		return b;
	}
	

}
