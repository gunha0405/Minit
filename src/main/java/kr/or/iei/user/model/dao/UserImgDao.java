package kr.or.iei.user.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserImg;
import kr.or.iei.user.model.dto.UserImgRowMapper;
import kr.or.iei.user.model.dto.UserRowMapper;

@Repository
public class UserImgDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private UserImgRowMapper userImgRowMapper;
	
	public int insertUserImg(int userNo) {
		String query = "insert into user_img "
				+ "(img_no, user_no, img_org, img_storage, img_type, create_date) "
				+ "values "
				+ "(user_img_seq.nextval,?,minit.logo,minit.logo,1,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {userNo};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public List<UserImg> selectUserImg(UserImg userImg) {
		String query = "select * from user_img "
				+ "where user_no=? "
				+ "order by update_date desc, create_date desc";
		Object[] params = {userImg.getUserNo()};
		List<UserImg> list = jdbc.query(query, userImgRowMapper, params);
		return list.isEmpty() ? null : list;
	}
	
	public int updateUserImg(UserImg userImg) {
		String query = "update user_img "
				+ "set "
				+ "img_org = ?, "
				+ "img_storage = ?, "
				+ "img_type = ?, "
				+ "update_date = to_char(sysdate,'yyyy-mm-dd') "
				+ "where user_no = ? "
				+ "and img_no = ?";
		Object[] params = {userImg.getImgOrg(), userImg.getImgStorage(), userImg.getImgType(), userImg.getUserNo(), userImg.getImgNo()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int deleteUserImg(UserImg userImg) {
		String query = "delete from user_img where user_id = ? ";
		if (userImg.getImgNo() > 0) {
			query += "and img_no = ?";
		}
		Object[] params = {userImg.getUserNo(), userImg.getImgNo()};
		int result = jdbc.update(query, params);
		return result;
	}
	/*
	public int insertUserImg(UserImg userImg) {
		String query = "insert into user_img "
				+ "(img_no, user_id, img_org, img_storage, img_type, create_date) "
				+ "values "
				+ "(user_img_seq.nextval,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'))"; //html에서 img_type을 1로 넣어서 (hidden 값으로)
		Object[] params = {userImg.getUserId(), userImg.getImgOrg(), userImg.getImgStorage(), userImg.getImgType()};
		int result = jdbc.update(query, params);
		return result;
	}
	 */
	
}