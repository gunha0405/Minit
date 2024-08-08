package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoRowMapper;


@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BoardRowMapper boardRowMapper;

	public List<Photo> PhotoList() {
		String query = "select * from photo_feed";
		List list = jdbc.query(query, boardRowMapper);
		return list;
	}

    public List<Board> getAllBoards() {
        String query = "select*from photo_feed order by reg_date desc"; // 전체 게시판 데이터 조회
        return jdbc.query(query, boardRowMapper);
    }

	public List<Board> getbestFeedBoards() {
		String query = "select * from photo_feed ";
		return null;
	}

    
    


}




