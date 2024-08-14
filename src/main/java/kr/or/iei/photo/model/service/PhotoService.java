package kr.or.iei.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.photo.model.dao.PhotoDao;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.photo.model.dto.PhotoRowMapper;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.text.model.dto.TextFeedComment;
import kr.or.iei.user.model.dto.User;

@Service
public class PhotoService {
    @Autowired
    private PhotoDao photoDao;

    @Transactional
    public int insertPhoto(Photo p, User user) {
        int result = photoDao.insertPhoto(p, user);
        return result;
    }

    public int insertComment(PhotoComment pc, User user, int photoFeedNo) {
        int result = photoDao.insertComment(pc, user, photoFeedNo);
        return result;
    }

    public List<Photo> selectPhotoFeed(int userNo) {
        List<Photo> photoList = photoDao.selectPhotoList();
        for (Photo photo : photoList) {
            List<PhotoComment> comments = photoDao.getCommentList(photo.getPhotoFeedNo());
            photo.setPhotoCommentList(comments);
            int isLike = photoDao.selectPhotoLike(photo.getPhotoFeedNo(), userNo);
            int isDec = photoDao.decCheck(photo.getPhotoFeedNo(), userNo);
            photo.setIsLike(isLike);
            photo.setIsDec(isDec);
        }
        return photoList;
    }

    public int deletePhoto(int photoFeedNo) {
        int result = photoDao.deletePhoto(photoFeedNo);
        return result;
    }

    public int updatePhoto(Photo photo) {
        int result = photoDao.updatePhoto(photo);
        return result;
    }

    public int likePush(int photoFeedNo, int isLike, int userNo) {
        int result = 0;
        if (isLike == 0) {
            if (!photoDao.isLikecheck(photoFeedNo, userNo)) {
                result = photoDao.insertPhotoLike(photoFeedNo, userNo);
            }
        } else if (isLike == 1) {
            result = photoDao.deletePhotoLike(photoFeedNo, userNo);
        }
        return result;
    }

    public boolean isLikecheck(int photoFeedNo, int userNo) {
        return photoDao.isLikecheck(photoFeedNo, userNo);
    }

    public List<PhotoComment> getCommentList(int photoFeedNo) {
        List<PhotoComment> cl = photoDao.getCommentList(photoFeedNo);
        return cl;
    }

    @Transactional
    public int updateComment(PhotoComment photoFeedCommentContent) {
        int result = photoDao.updateComment(photoFeedCommentContent);
        return result;
    }

    public int deleteComment(PhotoComment pc) {
        int result = photoDao.deleteComment(pc);
        return result;
    }

    public int contentsDec(int photoFeedNo, int userNo) {
        int result = 0;
        int isDec = photoDao.decCheck(photoFeedNo, userNo);
        if (isDec == 0) {
            if (!photoDao.contentsDec(photoFeedNo, userNo)) {
                result = photoDao.insertDec(photoFeedNo, userNo);
            }
        } else if (isDec == 1) {
            result = -100;
        }
        if (result > 0) {
	        int reportCount = photoDao.selectTextFeedReportCount(photoFeedNo);
	        if (reportCount >= 5) {
	            int hideTextFeedResult = photoDao.hideTextFeed(photoFeedNo);

	            if (hideTextFeedResult>0) {
	                result = -1000;
	            }
	        }
	    }
        return result;
    }
    
    
    public Photo getPhotoById(int photoFeedNo) {
    	Photo p = photoDao.getPhotoById(photoFeedNo);
        return p;
    }

	public int save(int photoFeedNo, int isSave, int userNo) {
		int result = 0;
        if (isSave == 0) {
            if (!photoDao.saveCheck(photoFeedNo, userNo)) {
                result = photoDao.insertSave(photoFeedNo, userNo);
            }
        } else if (isSave == 1) {
            result = photoDao.deleteSave(photoFeedNo, userNo);
        }
        return result;
	}

	public boolean saveCheck(int photoFeedNo, int userNo) {
		return photoDao.saveCheck(photoFeedNo, userNo);
	}

	public int getPhotoFeedCommentNo() {
		int photoFeedCommentNo = photoDao.getPhotoFeedCommentNo();
		return photoFeedCommentNo;
	}

	public PhotoComment selectOnePhotoFeedComment(int photoFeedCommentNo) {
		PhotoComment photoFeedComment = photoDao.selectOnePhotoFeedComment(photoFeedCommentNo);
		return photoFeedComment;
	}

	public List<Photo> selectReportFeed() {
		List<Photo> reportList = photoDao.selectReportFeed();
		return reportList;
	}

    
}

