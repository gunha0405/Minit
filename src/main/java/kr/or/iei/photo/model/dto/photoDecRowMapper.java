package kr.or.iei.photo.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class photoDecRowMapper implements RowMapper<Photo>{
	@Override
	public Photo mapRow(ResultSet rs,int rowNum) throws SQLException{
		Photo pd = new Photo();
		pd.setIsDec(rs.getInt("photo_commnet_feed_dec_No"));
		pd.setIsDec(rs.getInt("photo_comment_feed_dec_writer"));
		return pd;
	}
	
}
