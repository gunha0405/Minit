package kr.or.iei.photo.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoRowMapper;

@Repository
public class PhotoDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PhotoRowMapper photoRowMapper;
	public int insertPhoto(Photo p) {
		String query = "insert into photo_feed values(photo_feed_seq.nextval,?,?,0,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {p.getPhotoFeedImg(),p.getPhotoFeedWriter()};
		int result = jdbc.update(query,params);
		return result;
	}
}
