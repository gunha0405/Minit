package kr.or.iei.user.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserImgRowMapper implements RowMapper<UserImg> {

	@Override
	public UserImg mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserImg userImg = new UserImg();
		userImg.setImgNo(rs.getInt("img_no"));
		userImg.setUserId(rs.getString("user_id"));
		userImg.setImgType(rs.getInt("img_type"));
		userImg.setImgOrg(rs.getString("img_org"));
		userImg.setImgStorage(rs.getString("img_storage"));
		userImg.setCreateDate(rs.getString("create_date"));
		userImg.setUpdateDate(rs.getString("update_date"));
		return null;
	}	
}