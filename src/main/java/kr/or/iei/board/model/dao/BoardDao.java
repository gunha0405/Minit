package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.BestBoardRowMapper;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.board.model.dto.FollowingBoardRowMapper;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoRowMapper;


@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BoardRowMapper boardRowMapper;
    @Autowired
    private BestBoardRowMapper bestBoardRowMapper;


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
		String query = "";
		return null;
	}

	public List<Board> searchIdBoards() {
		String query = "";
		return null;
	}

}




