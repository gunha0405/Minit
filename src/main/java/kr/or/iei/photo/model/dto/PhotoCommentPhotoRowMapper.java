package kr.or.iei.photo.model.dto;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class PhotoCommentPhotoRowMapper implements RowMapper<PhotoComment> {
	
	@Override
	public PhotoComment mapRow(ResultSet rs,int rowNum) throws SQLException{
		PhotoComment comment = new PhotoComment();
		comment.setPhotoFeedCommentContent(rs.getString("photo_feed_comment_content"));
		comment.setPhotoFeedCommentDate(rs.getString("photo_feed_comment_date"));
		comment.setPhotoFeedCommentNo(rs.getInt("photo_feed_comment_no"));
		comment.setPhotoRef(rs.getInt("photo_ref"));
		comment.setPhotoFeedCommentWriter(rs.getString("photo_feed_comment_writer"));
		comment.setPhotoFeedCommentWriterImg(rs.getString("user_img"));
		return comment;
	}
	
}