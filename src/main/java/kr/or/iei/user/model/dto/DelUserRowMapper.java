package kr.or.iei.user.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DelUserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserNo(rs.getInt("user_no"));
		user.setUserId(rs.getString("user_id"));
		user.setUserPw(rs.getString("user_pw"));
		user.setUserName(rs.getString("user_name"));
		user.setUserEmail(rs.getString("user_email"));
		user.setCreateDate(rs.getString("create_date"));
		user.setCancelDate(rs.getString("cancel_date"));
		return user;
	}
	
	
}