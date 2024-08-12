package kr.or.iei.photo.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.dto.PhotoCommentPhotoRowMapper;
import kr.or.iei.photo.model.dto.PhotoCommentRowMapper;
import kr.or.iei.photo.model.dto.PhotoFeedReportRowMapper;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedReportRowMapper;
import kr.or.iei.user.model.dto.User;

@Repository
public class PhotoDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private PhotoRowMapper photoRowMapper;
    @Autowired
    private PhotoCommentRowMapper photoCommentRowMapper;
    @Autowired
    private PhotoCommentPhotoRowMapper photoCommentPhotoRowMapper;
    
	@Autowired
	private PhotoFeedReportRowMapper photoFeedReportRowMapper;

    public int insertPhoto(Photo p, User user) {
        String query = "INSERT INTO photo_feed VALUES(photo_feed_seq.NEXTVAL,?,?,0,TO_CHAR(SYSDATE,'YYYY-MM-DD'),0)";
        Object[] params = {p.getPhotoFeedImg(), user.getUserId()};
        int result = jdbc.update(query, params);
        return result;
    }

    public List<Photo> selectPhotoList() {
        String query = "SELECT * FROM photo_feed WHERE photo_feed_status = 0 ORDER BY 1";
        List<Photo> list = jdbc.query(query, photoRowMapper);
        return list;
    }

    public int deletePhoto(int photoFeedNo) {
        String query = "DELETE FROM photo_feed WHERE photo_feed_no=?";
        Object[] params = {photoFeedNo};
        int result = jdbc.update(query, params);
        return result;
    }

    public int updatePhoto(Photo p) {
        String query = "UPDATE photo_feed SET photo_feed_img=? WHERE photo_feed_no=?";
        Object[] params = {p.getPhotoFeedImg(), p.getPhotoFeedNo()};
        int result = jdbc.update(query, params);
        return result;
    }

    public int insertPhotoLike(int photoFeedNo, int userNo) {
        String query = "INSERT INTO photo_feed_like VALUES(?,?)";
        Object[] params = {photoFeedNo, userNo};
        int result = jdbc.update(query, params);
        return result;
    }

    public int deletePhotoLike(int photoFeedNo, int userNo) {
        String query = "DELETE FROM photo_feed_like WHERE photo_feed_like_no=? AND photo_feed_like_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int result = jdbc.update(query, params);
        return result;
    }

    public int selectPhotoLike(int photoFeedNo, int userNo) {
        String query = "SELECT COUNT(*) FROM photo_feed_like WHERE photo_feed_like_no=? AND photo_feed_like_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int isLike = jdbc.queryForObject(query, Integer.class, params);
        return isLike;
    }

    public boolean isLikecheck(int photoFeedNo, int userNo) {
        String query = "SELECT COUNT(*) FROM photo_feed_like WHERE photo_feed_like_no=? AND photo_feed_like_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int count = jdbc.queryForObject(query, Integer.class, params);
        return count > 0;
    }

    public int insertComment(PhotoComment pc, User user, int photoFeedNo) {
        String query = "INSERT INTO photo_comment_feed VALUES(photo_comment_feed_seq.NEXTVAL,?,?,TO_CHAR(SYSDATE,'YYYY-MM-DD'),?)";
        Object[] params = {user.getUserId(), pc.getPhotoFeedCommentContent(), photoFeedNo};
        int result = jdbc.update(query, params);
        return result;
    }

    public List<PhotoComment> getCommentList(int photoFeedNo) {
        String query = "select * from photo_comment_feed join user_tbl on photo_feed_comment_writer = user_id where photo_ref = ?";
        Object[] params = {photoFeedNo};
        List<PhotoComment> list = jdbc.query(query, photoCommentPhotoRowMapper, params);
        return list;
    }

    public int updateComment(PhotoComment photoFeedCommentContent) {
        String query = "UPDATE photo_comment_feed SET photo_feed_comment_content=? WHERE photo_feed_comment_no=?";
        Object[] params = {photoFeedCommentContent.getPhotoFeedCommentContent(), photoFeedCommentContent.getPhotoFeedCommentNo()};
        int result = jdbc.update(query, params);
        return result;
    }

    public int deleteComment(PhotoComment pc) {
        String query = "DELETE FROM photo_comment_feed WHERE photo_feed_comment_no=?";
        Object[] params = {pc.getPhotoFeedCommentNo()};
        int result = jdbc.update(query, params);
        return result;
    }

    public boolean contentsDec(int photoFeedNo, int userNo) {
        String query = "SELECT COUNT(*) FROM photo_feed_dec WHERE photo_feed_dec_no=? AND photo_feed_dec_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int count = jdbc.queryForObject(query, Integer.class, params);
        return count > 0;
    }

    public int insertDec(int photoFeedNo, int userNo) {
        String query = "INSERT INTO photo_feed_dec VALUES(?,?)";
        Object[] params = {photoFeedNo, userNo};
        int result = jdbc.update(query, params);
        return result;
    }

    public int decCheck(int photoFeedNo, int userNo) {
        String query = "SELECT COUNT(*) FROM photo_feed_dec WHERE photo_feed_dec_no=? AND photo_feed_dec_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int isDec = jdbc.queryForObject(query, Integer.class, params);
        return isDec;
    }

	public int selectTextFeedReportCount(int photoFeedNo) {
		String query = "SELECT COUNT(*) FROM photo_feed_dec WHERE photo_feed_dec_no=?";
		Object[] params = {photoFeedNo};
        int countDec = jdbc.queryForObject(query, Integer.class, params);
        return countDec;
	}

	public int hideTextFeed(int photoFeedNo) {
		String query = "update photo_feed set photo_feed_status = 1 where photo_feed_no=?";
		Object[] params = {photoFeedNo};
        int resultDec = jdbc.update(query, params);
        return resultDec;
	}
	public Photo getPhotoById(int photoFeedNo) {
	    String query = "SELECT * FROM photo_feed WHERE photo_feed_no=?";
	    Object[] params = {photoFeedNo};
	    return jdbc.queryForObject(query, photoRowMapper, params);
	}

	public boolean saveCheck(int photoFeedNo, int userNo) {
		String query = "SELECT COUNT(*) FROM photo_feed_save WHERE photo_feed_save_no=? AND photo_feed_save_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int count = jdbc.queryForObject(query, Integer.class, params);
        return count > 0;
	}

	public int insertSave(int photoFeedNo, int userNo) {
		String query = "INSERT INTO photo_feed_save VALUES(?,?)";
        Object[] params = {photoFeedNo, userNo};
        int result = jdbc.update(query, params);
        return result;
	}

	public int deleteSave(int photoFeedNo, int userNo) {
		String query = "DELETE FROM photo_feed_save WHERE photo_feed_save_no=? AND photo_feed_save_writer=?";
        Object[] params = {photoFeedNo, userNo};
        int result = jdbc.update(query, params);
        return result;
	}

	public int getPhotoFeedCommentNo() {
		String query = "select max(photo_feed_comment_no) from photo_comment_feed";
		int photoFeedNo = jdbc.queryForObject(query, Integer.class);
		return photoFeedNo;
	}

	public PhotoComment selectOnePhotoFeedComment(int photoFeedCommentNo) {
		String query = "select * from photo_comment_feed where photo_feed_comment_no = ?";
		Object[] params = {photoFeedCommentNo};
		List list = jdbc.query(query, photoCommentRowMapper, params);
		return (PhotoComment)list.get(0);
	}

	public List<Photo> selectReportFeed() {
		String query = "select photo_feed_IMG, photo_feed_no, user_no, photo_feed_writer, read_count,warning_count,reg_date from photo_feed join user_tbl on photo_feed_writer = user_id where photo_feed_status = 1";
		List<Photo> reportList = jdbc.query(query, photoFeedReportRowMapper);
		return reportList;
	}

	
}
