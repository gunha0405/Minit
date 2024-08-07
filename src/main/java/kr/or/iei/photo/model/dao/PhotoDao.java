package kr.or.iei.photo.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.user.model.dto.User;

@Repository
public class PhotoDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PhotoRowMapper photoRowMapper;
	public int insertPhoto(Photo p,User user) {
		String query = "insert into photo_feed values(photo_feed_seq.nextval,?,?,0,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {p.getPhotoFeedImg(),user.getUserId()};
		int result = jdbc.update(query,params);
		return result;
	}
	public static int insertComment(PhotoComment pc) {
		// TODO Auto-generated method stub
		return 0;
	}
	public List selectPhotoList() {
		String query = "select * from photo_feed order by 1";
		List list = jdbc.query(query, photoRowMapper);
		return list;
	}
	public int deletePhoto(int photoFeedNo) {
		String query = "delete from photo_feed where photo_feed_no=?";
		Object[] params = {photoFeedNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int updatePhoto(Photo p) {
        String query = "update photo_feed set photo_feed_img = ? where photo_feed_no = ?";
        Object[] params = {p.getPhotoFeedImg(), p.getPhotoFeedNo()};
        int result = jdbc.update(query, params);
        return result;
    }
	public int insertPhotoLike(int photoFeedNo, int userNo) {
		String query = "insert into photo_feed_like values(?,?)";
		Object[] params = {photoFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int deletePhotoLike(int photoFeedNo, int userNo) {
		String query = "delete from photo_feed_like where photo_feed_like_no=? and user_no=?";
		Object[] params = {photoFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}
}
