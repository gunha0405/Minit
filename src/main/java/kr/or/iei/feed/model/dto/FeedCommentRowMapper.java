package kr.or.iei.feed.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class FeedCommentRowMapper implements RowMapper<FeedComment>{

	@Override
	public FeedComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeedComment fc = new FeedComment();
		fc.setFeedCommentCountent(rs.getString("feed_comment_countent"));
		fc.setFeedCommentDate(rs.getString("feed_comment_date"));
		fc.setFeedCommentNo(rs.getInt("feed_comment_no"));
		fc.setFeedCommentWriter(rs.getString("feed_comment_writer"));
		fc.setFeedRef(rs.getInt("feed_ref"));
		fc.setUserId(rs.getString("user_id"));
		fc.setUserImg(rs.getString("user_img"));
		return fc;
	}

}
