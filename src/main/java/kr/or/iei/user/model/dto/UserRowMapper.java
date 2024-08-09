package kr.or.iei.user.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserNo(rs.getInt("user_no"));
		user.setUserId(rs.getString("user_id"));
		user.setUserPw(rs.getString("user_pw"));
		user.setUserName(rs.getString("user_name"));
		user.setUserNick(rs.getString("user_nick"));
		user.setUserInfo(rs.getString("user_info"));
		user.setUserEmail(rs.getString("user_email"));
		user.setWarningCount(rs.getInt("warning_count"));
		user.setUserLevel(rs.getInt("user_level"));
		user.setCreateDate(rs.getString("create_date"));
		user.setUpdateDate(rs.getString("update_date"));
		user.setUserImg(rs.getString("user_img"));
		return user;
	}
	
	
}