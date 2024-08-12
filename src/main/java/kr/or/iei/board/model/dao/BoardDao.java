package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.BestBoardRowMapper;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.board.model.dto.FollowingBoardRowMapper;
import kr.or.iei.board.model.dto.SearchIdRowMapper;
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


	public List<Photo> PhotoList() {
		String query = "select * from photo_feed";
		List list = jdbc.query(query, boardRowMapper);
		return list;
	}

    public List<Board> getAllBoards() {
        String query = "select*from photo_feed order by reg_date desc"; // 전체 게시판 데이터 조회
        return jdbc.query(query, boardRowMapper);
    }

	public List<Board> BestBoards() {
		String query = "select p.*,(select count(*) from photo_feed_like where photo_feed_like_no=p.photo_feed_no) as total_likes from (select * from photo_feed)p order by total_likes desc"; 
		

		return jdbc.query(query, bestBoardRowMapper);
	}

	public List<Board> followingBoards() {
		String query = "select*from user_feed_tbl order by user_feed_date desc";
		return jdbc.query(query, followingBoardRowMapper);
	}

	public List searchList(User user) {
		System.out.println("dao : "+user);
		String query = "select * from (select rownum as rnum, p.* from (select * from photo_feed p2 where photo_feed_writer = (select user_id from user_tbl where user_id = ?) order by reg_date desc)p)";
		Object[] params = {user.getUserId()};
		List list = jdbc.query(query, searchIdRowMapper,params);
		System.out.println("photofeedNO 찾자 시발"+list);
		return list;
	}
	
	public int BestBoards2(int photoFeedNo) {
		String query = "select (select count(*) from photo_feed_like where photo_feed_like_no=p.photo_feed_no) as total_likes from (select * from photo_feed where photo_feed_no=?)p order by total_likes desc"; 
		Object[] params = {photoFeedNo};
		int totalLikes = jdbc.queryForObject(query, Integer.class,params); 
		System.out.println(totalLikes);
		return totalLikes;
	}

	

	

}




