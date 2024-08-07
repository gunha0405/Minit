package kr.or.iei.user.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private UserRowMapper userRowMapper;
	
	public User selectOneUser(User u) {
		String query = "select * from user_tbl where user_id=? and user_pw=?";
		Object[] params = {u.getUserId(), u.getUserPw()};
		List list = jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);			
		}
	}

	public User selectOneUser(String userNick) {
		String query = "select * from user_tbl where user_nick=?";
		Object[] params = {userNick};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public int insertUser(User u) {
		String query = "insert into user_tbl "
				+ "(user_no, user_id, user_pw, user_name, user_nick, user_email, create_date) "
				+ "values "
				+ "(user_tbl_seq.nextval,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {u.getUserId(), u.getUserPw(), u.getUserName(), u.getUserNick(), u.getUserEmail()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateUser(User u) {
		String query = "update user_tbl set user_nick=?, user_info=?, user_pw=?, update_date=to_char(sysdate,'yyyy-mm-dd') where user_no=?";
		Object[] params = {u.getUserNick(),u.getUserInfo(),u.getUserPw(),u.getUserNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteUser(User user) {
		String query = "delete from user_tbl where user_id = ?";
		Object[] params = {user.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public User selectUser(User user) {
		String query = "select * from user_tbl "
				+ "where user_id=? "
				+ "order by update_date desc, create_date desc";
		Object[] params = {user.getUserId()};
		List list = jdbc.query(query, userRowMapper, params);
		return list.isEmpty() ? null : (User)list.get(0);
	}
	
	public int insertUser(User user) {
		String query = "insert into user_tbl "
				+ "(user_id, user_pw, user_name, user_nick, user_info, user_email, create_date) "
				+ "values "
				+ "(?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {user.getUserId(), user.getUserPw(), user.getUserName(), user.getUserNick(), user.getUserInfo(), user.getUserEmail()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int updateUser(User user) {
		String query = "update user_tbl "
				+ "set "
				+ "user_pw = ?, "
				+ "user_name = ?, "
				+ "user_nick = ?, "
				+ "user_info = ?, "
				+ "user_level = ?, "
				+ "warning_count = ?,"
				+ "update_date = to_char(sysdate,'yyyy-mm-dd') "
				+ "where user_id = ?";
		Object[] params = {user.getUserPw(), user.getUserName(), user.getUserNick(), user.getUserInfo(), user.getUserLevel(), user.getWarningCount(), user.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int deleteUser(User user) {
		String query = "delete from user_tbl where user_id = ?";
		Object[] params = {user.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}

	*/
	
}