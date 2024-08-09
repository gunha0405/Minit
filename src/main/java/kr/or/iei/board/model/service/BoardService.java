package kr.or.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;

@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;

	public List<Board> getAllBoards() {
		return boardDao.getAllBoards();
	}

	public List<Board> BestBoards() {
		
		return boardDao.BestBoards();
	}

	public List<Board> followingBoards() {
		
		return boardDao.followingBoards();
	}

	public List<Board> searchIdBoards() {
		
		return boardDao.searchIdBoards();
	}



	
}

    
    
    










