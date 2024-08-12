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
		b.setUserFeedWriter(rs.getString("photo_feed_writer"));
		b.setRegDate(rs.getString("reg_date"));
		b.setPhotoFeedImg(rs.getString("photo_feed_img"));	
		b.setPhotoFeedNo(rs.getInt("photo_feed_no"));

		//b.setTotalLikes(rs.getInt("total_likes"));
		
		return b;
	}
	

}
