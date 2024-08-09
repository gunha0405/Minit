package kr.or.iei.photo.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class PhotoSaveRowMapper implements RowMapper<Photo>{
	@Override
	public Photo mapRow(ResultSet rs,int rowNum) throws SQLException{
		Photo pl = new Photo();
		pl.setIsSave(rs.getInt("photo_feed_save"));
		return pl;
	}
	
}
