package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class FeedCommentViewRowMapper implements RowMapper<FeedComment>{

	@Override
	public FeedComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeedComment fc = new FeedComment();
		fc.setFeedCommentContent(rs.getString("feed_comment_content"));
		fc.setFeedCommentDate(rs.getString("feed_comment_date"));
		fc.setFeedCommentNo(rs.getInt("feed_comment_no"));
		fc.setFeedCommentWriter(rs.getString("feed_comment_writer"));
		fc.setFeedRef(rs.getInt("feed_ref"));
		fc.setUserImg(rs.getString("user_img"));
		return fc;
	}

}
