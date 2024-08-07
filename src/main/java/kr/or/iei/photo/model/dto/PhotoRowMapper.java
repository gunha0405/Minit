package kr.or.iei.photo.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoRowMapper implements RowMapper<Photo> {
	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo p = new Photo();
		p.setPhotoFeedNo(rs.getInt("photo_feed_no"));
		p.setPhotoFeedImg(rs.getString("photo_feed_img"));
		p.setPhotoFeedWriter(rs.getString("photo_feed_writer"));
		p.setReadCount(rs.getInt("read_count"));
		p.setRegDate(rs.getString("reg_date"));
		return p;
	}
}
