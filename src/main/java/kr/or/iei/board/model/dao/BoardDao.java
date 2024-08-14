package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.AllFeedRowMapper;
import kr.or.iei.board.model.dto.BestBoardRowMapper;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.board.model.dto.FollowingBoardRowMapper;
import kr.or.iei.board.model.dto.FollowingIdRowMapper;
import kr.or.iei.board.model.dto.SearchFeedRowMapper;
import kr.or.iei.board.model.dto.SearchIdRowMapper;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFileRowMapper;
import kr.or.iei.feed.model.dto.FeedRowMapper;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.user.model.dto.User;


@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BoardRowMapper boardRowMapper;
    @Autowired
    private BestBoardRowMapper bestBoardRowMapper;
    @Autowired
    private FollowingBoardRowMapper followingBoardRowMapper;
    @Autowired
    private SearchIdRowMapper searchIdRowMapper;
    @Autowired
    private FollowingIdRowMapper followingIdRowMapper;
    @Autowired
    private FeedRowMapper feedRowMapper;
    @Autowired
    private PhotoRowMapper photoRowMapper;
    @Autowired
    private AllFeedRowMapper allFeedRowMapper;
    @Autowired
    private FeedFileRowMapper feedFileRowMapper;
    @Autowired
    private SearchFeedRowMapper searchFeedRowMapper;

  

	public List<Board> BestBoards() {
		String query = "select p.*,(select count(*) from photo_feed_like where photo_feed_like_no=p.photo_feed_no) as total_likes from (select * from photo_feed)p order by total_likes desc"; 
		return jdbc.query(query, bestBoardRowMapper);
	}
	public int BestBoards2(int photoFeedNo) {
		String query = "select (select count(*) from photo_feed_like where photo_feed_like_no=p.photo_feed_no) as total_likes from (select * from photo_feed where photo_feed_no=?)p order by total_likes desc"; 
		Object[] params = {photoFeedNo};
		int totalLikes = jdbc.queryForObject(query, Integer.class,params); 
		
		return totalLikes;
	}

	
	//검색기능
	public List searchList(User user) {
		
		String query = "select * from (select rownum as rnum, p.* from (select * from photo_feed p2 where photo_feed_writer = (select user_id from user_tbl where user_id = ?) order by reg_date desc)p)";
		Object[] params = {user.getUserId()};
		List list = jdbc.query(query, searchIdRowMapper,params);
		return list;
	}
	//검색기능2
	public List searchList2(User user) {
		
		String query = "select * from user_feed_tbl u1\r\n" + 
				"join user_feed_file  u2 on u1.user_feed_no =u2.user_feed_no\r\n"
				+ "where user_feed_writer=? ";
		Object[] params = {user.getUserId()};
		List list = jdbc.query(query, searchFeedRowMapper, params);
		
		
		return list;
	}
	

	

	public List<Board> searchFollowingId(String userId) {
		String query = "select * from follow where user_id = ?"; 
		Object[] params = {userId};
		List list = jdbc.query(query, followingIdRowMapper,params);
		
		return list;
	}

	public List<Board> searchFollowingFeed(String following) {
		String query = "select * from user_feed_tbl where user_feed_writer = ?";
		Object[] params = {following};
		List list = jdbc.query(query, feedRowMapper,params );
		
		return list;
	}

	public List<Photo> photoList() {
		String query = "select * from photo_feed order by reg_date desc";
		List<Photo> list = jdbc.query(query, photoRowMapper);
		return list;
	}

	public List<Feed> boardList() {
		String query = "select * from user_feed_tbl order by user_feed_date";
		List<Feed> list = jdbc.query(query, feedRowMapper);
		return list;
	}

	public int selectFeedTotal() {
		String query = "select count(*) from user_feed_tbl";
		int total = jdbc.queryForObject(query, Integer.class);
		return total;
	}

	public Feed getfeed(int i) {
		
		String query = "select user_feed_filepath, user_feed_writer, USER_FEED_CONTENT, user_feed_date\r\n" + 
				"from \r\n" + 
				"(select rownum , n.*from\r\n" + 
				"((select * from\r\n" + 
				"(select rownum as rnum, n.*from (select * from user_feed_tbl)n order by user_feed_no)\r\n" + 
				"join user_feed_file using (user_feed_no) where rnum=?)n) where rownum = 1)";
		Object [] params = {i};
		List list = jdbc.query(query, allFeedRowMapper, params);
		
		
		return (Feed)list.get(0);
	}


	

	

}




