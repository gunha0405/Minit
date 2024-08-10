package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.or.iei.user.model.dto.User;
@Component
public class FollowUserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User();
		u.setUserId(rs.getString("user_id"));
		u.setUserImg(rs.getString("user_img"));
		return u;
	}


}
