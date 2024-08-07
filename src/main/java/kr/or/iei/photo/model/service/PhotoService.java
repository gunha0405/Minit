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
	public static int insertComment(PhotoComment pc) {
		int result = PhotoDao.insertComment(pc);
		return result;
	}
	public List selectPhotoFeed() {
		List photoList = photoDao.selectPhotoList();
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
}
