package kr.or.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.user.model.dto.User;

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
	public List searchList(User user) {
        List<Board> list = boardDao.searchList(user);
        for(Board b : list) {
        	int totalLikes = boardDao.BestBoards2(b.getPhotoFeedNo());
        	b.setTotalLikes(totalLikes);
        }
        System.out.println(list);
        return list;
    }





	
}

    
    
    










