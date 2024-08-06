package kr.or.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.text.model.dto.TextFeed;

@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;

    public List<Board> getAllBoards() {
        return boardDao.getAllBoards();
    }
}





