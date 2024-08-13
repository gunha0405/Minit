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

	public List<TextFeed> selectTextFeed(int userNo) {
        List<TextFeed> textFeedList = textDao.selectTextFeed();
        for (TextFeed textFeed : textFeedList) {
        	List<TextFeedComment> comments = textDao.selectTextFeedComment(textFeed.getTextFeedNo());
        	for(TextFeedComment textFeedComment : comments) {
        		int isLike = textDao.selectTextFeedCommentLikeStatus(textFeedComment.getTextFeedCommentNo(), userNo);
        		int likeCount = textDao.countCommentLike(textFeedComment.getTextFeedCommentNo());
        		textFeedComment.setIsLike(isLike);
         		textFeedComment.setLikeCount(likeCount);
        	}
            textFeed.setTextFeedCommentList(comments);
        	int isLike = textDao.selectTextFeedLikeStatus(textFeed.getTextFeedNo(), userNo);
        	int isReport = textDao.selectTextFeedReportStatus(textFeed.getTextFeedNo(), userNo);
        	int isSave = textDao.selectTextFeedSaveStatus(textFeed.getTextFeedNo(), userNo);
        	int likeCount = textDao.countLike(textFeed.getTextFeedNo());
            textFeed.setIsLike(isLike);
            textFeed.setIsReport(isReport);
            textFeed.setIsSave(isSave);
            textFeed.setLikeCount(likeCount);
        }
        return textFeedList;
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
	@Transactional
	public int deleteTextFeedComment(int textFeedCommentNo) {
		int result = textDao.deleteTextFeedComment(textFeedCommentNo);
		return result;
	}
	@Transactional
	public int editTextFeed(String textFeedEditContent, int textFeedEditNo) {
		int result = textDao.editTextFeed(textFeedEditContent, textFeedEditNo);
		return result;
	}
	@Transactional
	public int editTextFeedComment(String textFeedCommentEditContent, int textFeedCommentEditNo) {
		int result = textDao.editTextFeedComment(textFeedCommentEditContent, textFeedCommentEditNo);
		return result;
	}
	@Transactional
	public int textFeedLikePush(int textFeedNo, int isLike, int userNo) {
	    int result = 0;
	    if (isLike == 0) {
	        // 좋아요를 누르지 않은 상태
	        if (!textDao.isLikeFeedExists(textFeedNo, userNo)) {
	            result = textDao.insertTextFeedLike(textFeedNo, userNo);
	        }
	    } else if (isLike == 1) {
	        // 좋아요가 눌러진 상태
	        result = textDao.deleteTextFeedLike(textFeedNo, userNo);
	    }
	    if(result > 0) {
	    	int likeCount = textDao.countLike(textFeedNo);
	    	return likeCount;
	    }else {
	    	return -1;
	    }
	}
	@Transactional
	public int textFeedCommentLikePush(int textFeedCommentNo, int isLike, int userNo) {
		int result = 0;
		if(isLike == 0) {
			if(!textDao.isLikeCommentExists(textFeedCommentNo, userNo)) {
				result = textDao.insertTextFeedCommentLike(textFeedCommentNo, userNo);
			}
		} else if(isLike == 1) {
			result = textDao.deleteTextFeedCommentLike(textFeedCommentNo,userNo);
		}
		if(result>0) {
			int likeCount = textDao.countCommentLike(textFeedCommentNo);
			return likeCount;
		}else {
			return -1;
		}
	}
	
	@Transactional
	public int textFeedReport(int textFeedNo, int userNo, int isReport) {
	    int result = 0;
	    if (isReport == 0) {
	        if (!textDao.isReportFeedExists(textFeedNo, userNo)) {
	            result = textDao.insertTextFeedReport(textFeedNo, userNo);
	        }
	    } else if (isReport == 1) {
	        result = -100;
	    }
	    if (result > 0) {
	        int reportCount = textDao.selectTextFeedReportCount(textFeedNo);
	        if (reportCount >= 5) {
	            int hideTextFeedResult = textDao.hideTextFeed(textFeedNo);

	            if (hideTextFeedResult>0) {
	                result = -1000;
	            }
	        }
	    }
	    return result;
	}

	@Transactional
	public int textFeedSave(int textFeedNo, int userNo, int isSave) {
		int result = 0;
		if(isSave == 0) {
			if(!textDao.isSaveFeedExists(textFeedNo, userNo))
				result = textDao.insertTextFeedSave(textFeedNo, userNo);
		} else if(isSave == 1) {
			result = textDao.deleteTextFeedSave(textFeedNo, userNo);
		}
		return result;
	}

	public List<TextFeed> selectReportFeed() {
		List<TextFeed> reportList = textDao.selectReportFeed();
		return reportList;
	}

	public int likeCount(int textFeedNo) {
		int likeCount = textDao.countLike(textFeedNo);
		return likeCount;
	}
	
	
	
}
