package kr.or.iei.user.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dto.DelUserRowMapper;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private UserRowMapper userRowMapper;
	@Autowired
	private DelUserRowMapper delUserRowMapper;
	
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

	public User selectUserNick(String userNick) {
		String query = "select * from user_tbl where user_nick=?";
		Object[] params = {userNick};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public User selectUserId(String userId) {
		String query = "select * from user_tbl where user_id=?";
		Object[] params = {userId};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public User selectUserEmail(String receiver) {
		String query = "select * from user_tbl where user_email=?";
		Object[] params = {receiver};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public User searchDelUserEmail(String receiver) {
		String query = "select * from user_del_tbl where user_email=?";
		Object[] params = {receiver};
		List list =  jdbc.query(query, delUserRowMapper, params);
		System.out.println(list);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}
	
	public int insertUser(User u) {
		String query = "insert into user_tbl "
				+ "(user_no, user_id, user_pw, user_name, user_nick, user_email, create_date, user_img) "
				+ "values "
				+ "(user_tbl_seq.nextval,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),'minit_logo.png')";
		Object[] params = {u.getUserId(), u.getUserPw(), u.getUserName(), u.getUserNick(), u.getUserEmail()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectUserNo(String userId) {
		String query = "select user_no from user_tbl where user_id=?";
		Object[] params = {userId};
		int userNo = jdbc.update(query, params);
		return userNo;
	}

	public int updateUserAll(User u) {
		String query = "update user_tbl set user_nick=?, user_info=?, user_pw=?, update_date=to_char(sysdate,'yyyy-mm-dd'), user_img=? where user_no=?";
		Object[] params = {u.getUserNick(),u.getUserInfo(),u.getUserPw(),u.getUserImg(),u.getUserNo()};
		int result = jdbc.update(query, params);
		System.out.println("이미지 변경");
		return result;
	}
	public int updateUserBasic(User u) { //기본이미지 사용
		String query = "update user_tbl set user_nick=?, user_info=?, user_pw=?, update_date=to_char(sysdate,'yyyy-mm-dd'), user_img='minit_logo.png' where user_no=?";
		Object[] params = {u.getUserNick(),u.getUserInfo(),u.getUserPw(),u.getUserNo()};
		int result = jdbc.update(query, params);
		System.out.println("기본이미지로");
		return result;
	}
	public int updateUserReturn(User u) { //이미지 업데이트 x
		String query = "update user_tbl set user_nick=?, user_info=?, user_pw=?, update_date=to_char(sysdate,'yyyy-mm-dd') where user_no=?";
		Object[] params = {u.getUserNick(),u.getUserInfo(),u.getUserPw(),u.getUserNo()};
		int result = jdbc.update(query, params);
		System.out.println("이미지 업데이트x");
		return result;
	}

	public int delInsertUser(User user) {
		String query = "insert into user_del_tbl values(?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {user.getUserNo(),user.getUserId(),user.getUserPw(),user.getUserName(),user.getUserEmail(),user.getCreateDate()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int deleteUser(User user) {
		String query = "delete from user_tbl where user_id = ?";
		Object[] params = {user.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}

	public User searchUserId(User u) {
		String query = "select * from user_tbl where user_name=? and user_email=?";
		Object[] params = {u.getUserName(), u.getUserEmail()};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public User ajaxFindUser(String userId, String userEmail) {
		String query = "select * from user_tbl where user_id=? and user_email=?";
		Object[] params = {userId, userEmail};
		List list =  jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}

	public int updatePw(User u) {
		String query = "update user_tbl set user_pw=?, update_date=to_char(sysdate,'yyyy-mm-dd') where user_id=?";
		Object[] params = {u.getUserPw(),u.getUserId()};
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectAllUser() {
		String query = "select * from user_tbl order by 1";
		List list = jdbc.query(query, userRowMapper);
		return list;
	}

	public User selectOneUser(int userNo) {
		String query = "select * from user_tbl where user_no=?";
		Object[] params = {userNo};
		List list = jdbc.query(query, userRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (User)list.get(0);
		}
	}
	
	public int changeCount(User u) {
		String query = "update user_tbl set warning_count=(?+1) where user_no=?";
		Object[] params = {u.getWarningCount(), u.getUserNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int warningCount(User u) {
		String query ="select warning_count from user_tbl where user_no=?";
		Object[] params = {u.getUserNo()};
		int warningCount = jdbc.queryForObject(query, Integer.class,params);
		System.out.println(warningCount);
		return warningCount;
	}

	public int updateLevel(User u) {
		String query = "update user_tbl set user_level=3 where warning_count=5 and user_no=?";
		Object[] params = {u.getUserNo()};
		int result = jdbc.update(query,params);
		System.out.println("경고 처리값 :" +result);
		return result;
	}

	public int deleteTextFeed(int textFeedNo) {
		String query = "delete from text_feed where text_feed_no=?";
		Object[] params = {textFeedNo};
		int result = jdbc.update(query, params);
		System.out.println("삭제 처리값 :" +result);
		return result;
	}
	
	public int deletePhotoFeed(int photoFeedNo) {
		String query = "delete from photo_feed where photo_feed_no=?";
		Object[] params = {photoFeedNo};
		int result = jdbc.update(query, params);
		System.out.println("삭제 처리값 :" +result);
		return result;
	}
	
	public int deleteUserFeed(int userFeedNo) {
		String query = "delete from user_feed_tbl where user_feed_no=?";
		Object[] params = {userFeedNo};
		int result = jdbc.update(query, params);
		System.out.println("삭제 처리값 :" +result);
		return result;
	}

	public int changeLevel(User u) {
		String query = "update user_tbl set user_level=?, warning_count=0 where user_no=?";
		Object[] params = {u.getUserLevel(),u.getUserNo()};
		int result = jdbc.update(query,params);
		return result;
	}

}