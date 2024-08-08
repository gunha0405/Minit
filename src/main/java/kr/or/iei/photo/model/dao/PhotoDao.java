package kr.or.iei.photo.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.dto.PhotoCommentRowMapper;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeedComment;
import kr.or.iei.user.model.dto.User;

@Repository
public class PhotoDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PhotoRowMapper photoRowMapper;
	@Autowired
	private PhotoCommentRowMapper photoCommentRowMapper;
	
	
	
	public int insertPhoto(Photo p,User user) {
		String query = "insert into photo_feed values(photo_feed_seq.nextval,?,?,0,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {p.getPhotoFeedImg(),user.getUserId()};
		int result = jdbc.update(query,params);
		return result;
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
		String query = "delete from photo_feed_like where photo_feed_like_no=? and photo_feed_like_writer=?";
		Object[] params = {photoFeedNo, userNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int selectPhotoLike(int photoFeedNo,int userNo) {
		String query = "SELECT COUNT(*) FROM photo_feed_like WHERE photo_feed_like_no = ? AND photo_feed_like_writer = ?";
		Object[] params = {photoFeedNo, userNo};
        int isLike = jdbc.queryForObject(query,Integer.class ,params);
        return isLike;
	}
	public boolean isLikecheck(int photoFeedNo, int userNo) {
		String query = "SELECT COUNT(*) FROM photo_feed_like WHERE photo_feed_like_no = ? AND photo_feed_like_writer = ?";
		Object[] params = { photoFeedNo, userNo };
	    int count = jdbc.queryForObject(query,Integer.class ,params);
	    return count > 0;
	    
	}
	
	public int insertComment(PhotoComment pc, User user,int photoFeedNo) {
		String query = "insert into photo_comment_feed values(photo_comment_feed_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object[] params = {user.getUserId(),pc.getPhotoFeedCommentContent(), photoFeedNo};
		int result = jdbc.update(query, params);
		return result;
	}


	public List<PhotoComment> getCommentList(int photoFeedNo) {
		String query = "select * from photo_comment_feed where photo_ref = ?";
		Object[] params = {photoFeedNo};
		List list = jdbc.query(query, photoCommentRowMapper, params);
		return list;
	}




	public int updateComment(PhotoComment photoFeedCommentContent) {
		String query = "update photo_comment_feed set photo_feed_comment_content=? where photo_feed_comment_no=?";
		Object [] params = {photoFeedCommentContent.getPhotoFeedCommentContent(),photoFeedCommentContent.getPhotoFeedCommentNo()};
		System.out.println(photoFeedCommentContent);
		int result = jdbc.update(query,params);
		return result;
	}



	public int deleteComment(PhotoComment pc) {
		String query = "delete from photo_comment_feed where photo_feed_comment_no=?";
		Object[]params = {pc.getPhotoFeedCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	
	
}
