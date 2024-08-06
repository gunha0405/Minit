package kr.or.iei.text.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.text.model.dao.TextDao;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedComment;
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
    public int textFeedWrite(String textFeedContent, User user) {
        
        int result = textDao.textFeedWrite(textFeedContent, user);
        
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
	@Transactional
	public int textFeedCommentWrite(String textFeedCommentContent, int textFeedNo,User user) {
		int result = textDao.textFeedCommentWrite(textFeedCommentContent, textFeedNo, user);
		return result;
	}
	public int getTextFeedCommentNo() {
		int textFeedCommentNo = textDao.getTextFeedCommentNo();
		return textFeedCommentNo;
	}
	public TextFeedComment selectOnetTextFeedComment(int textFeedCommentNo) {
		TextFeedComment textFeedComment = textDao.selectOneTextFeedComment(textFeedCommentNo);
		return textFeedComment;
	}
	
	public List<TextFeedComment> selectTextFeedComment(int textFeedNo) {
		List<TextFeedComment> list = textDao.selectTextFeedComment(textFeedNo);
		return list;
	}
	
}
