package kr.or.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardRowMapper;
import kr.or.iei.text.model.dto.TextFeed;

@Repository
public class BoardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BoardRowMapper boardRowMapper;

    public List<Board> getAllBoards() {
        String query = "SELECT * FROM photo_feed "; // 전체 게시판 데이터 조회
        return jdbcTemplate.query(query, boardRowMapper);
    }
}



