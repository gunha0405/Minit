package kr.or.iei.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.text.model.dto.TextFeed;

@Service
public class BoardService {
	private BoardDao boardDao;

	public List<TextFeed> AllTextFeeds() {
		List list = boardDao.AllTextFeeds();
		return list;
	}
	/*
	public List allTextFeed() {
		List list = boardDao.allTextfeed();
		return list;
	}
	*/
 
}





