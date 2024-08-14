package kr.or.iei.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.user.model.dto.User;

@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;
  

	public List<Board> BestBoards() {
		
		return boardDao.BestBoards();
	}
	
	public List searchList(User user) {
        List<Board> list = boardDao.searchList(user);
       
        
        return list;
    }
	public List searchList2(User user) {
        List<Board> list = boardDao.searchList2(user);
        
       
        return list;
    }

	public ArrayList<String> searchFollowingId(String userId) {
		List<Board> list = boardDao.searchFollowingId(userId);
		ArrayList<String> followingid = new ArrayList<String>();
		for(Board b : list) {
			followingid.add(b.getFollowingId());
		}
		
		return followingid;
	}

	public List<Board> followingBoards(ArrayList<String> followingid) {
	    List<Board> list = new ArrayList<>();
	    for (String following : followingid) {
	        List<Board> boardlist = boardDao.searchFollowingFeed(following);
	        list.addAll(boardlist); // boardlist의 모든 게시글을 list에 추가
	    }
	    System.out.println(list);
	    return list; // 수정된 반환값
	



	}
	public List<Photo> photoList() {
		
		return boardDao.photoList();
	}
	public List<Feed> boardList() {
		int total = boardDao.selectFeedTotal();
		List<Feed> feedList = new ArrayList<Feed>();
		for(int i = 0; i < total; i ++) {
			Feed f = new Feed();
			f = boardDao.getfeed(i+1);
			feedList.add(f);
			
		}
		return feedList;
	}

	
}

    
    
    










