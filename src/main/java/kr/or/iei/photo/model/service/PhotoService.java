package kr.or.iei.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.photo.model.dao.PhotoDao;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;
import kr.or.iei.user.model.dto.User;

@Service
public class PhotoService {
	@Autowired
	private PhotoDao photoDao;
	@Transactional
	public int insertPhoto(Photo p,User user) {
		// TODO Auto-generated method stub
		int result = photoDao.insertPhoto(p,user);
		return result;
	}
	public int insertComment(PhotoComment pc,User user,int photoFeedNo) {
		int result = photoDao.insertComment(pc,user,photoFeedNo);
		return result;
	}
	public List<Photo> selectPhotoFeed(int userNo) {
		List<Photo> photoList = photoDao.selectPhotoList();
		for(Photo photo : photoList) {
			List<PhotoComment> comments = photoDao.getCommentList(photo.getPhotoFeedNo());
			photo.setPhotoCommentList(comments);
			int isLike = photoDao.selectPhotoLike(photo.getPhotoFeedNo(),userNo);
			photo.setIsLike(isLike);
		}
		return photoList;
	}
	public int deletePhoto(int photoFeedNo) {
		int result = photoDao.deletePhoto(photoFeedNo);
		return result;
	}
	public int updatePhoto(Photo photoFeedNo) {
		int result = photoDao.updatePhoto(photoFeedNo);
		return result;
	}
	public int likePush(int photoFeedNo, int isLike, int userNo) {
		int result = 0;
		if(isLike == 0) {
			if(!photoDao.isLikecheck(photoFeedNo,userNo)) {
				result = photoDao.insertPhotoLike(photoFeedNo,userNo);
			}
		}else if(isLike ==1) {
			result = photoDao.deletePhotoLike(photoFeedNo,userNo);
		}
		return result;
	}
	public boolean isLikecheck(int photoFeedNo, int userNo) {
        return photoDao.isLikecheck(photoFeedNo, userNo);
    }
	public List<PhotoComment> getCommentList(int photoFeedNo) {
		List cl = photoDao.getCommentList(photoFeedNo);
		return cl;
	}
	@Transactional
	public int updateComment(PhotoComment photoFeedCommentContent) {
		int result = photoDao.updateComment(photoFeedCommentContent);
		return result;
	}
	public int deleteComment(PhotoComment pc) {
		int result = photoDao.deleteComment(pc);
		System.out.println(pc);
		return result;
	}
	
}
