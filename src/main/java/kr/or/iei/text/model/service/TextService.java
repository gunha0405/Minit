package kr.or.iei.text.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.text.model.dao.TextDao;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dto.User;

@Service
public class TextService {
	@Autowired
	private TextDao textDao;

	public List selectTextFeed() {
		List list = textDao.selectTextFeed();
		return list;
	}
	@Transactional
    public int insertTextFeed(String textFeedContent, User user) {
        
        int result = textDao.insertTextFeed(textFeedContent, user);
        
        return result;
    }
	
	//가장 최근에 작성된 글 번호를 가져오는 메소드
	public int getTextFeedNo() {
		int textFeedNo = textDao.getTextFeedNo();
		return textFeedNo;
	}
	
	public TextFeed selectOneTextFeed(int textFeedNo) {
		TextFeed textFeed = textDao.selectOneTextFeed(textFeedNo);
		return textFeed;
	}
	@Transactional
	public int deleteTextFeed(int textFeedNo) {
		int result = textDao.deleteTextFeed(textFeedNo);
		return result;
		
	}
	
}
