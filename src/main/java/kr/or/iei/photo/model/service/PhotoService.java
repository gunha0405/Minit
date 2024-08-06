package kr.or.iei.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.photo.model.dao.PhotoDao;
import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.photo.model.dto.PhotoComment;

@Service
public class PhotoService {
	@Autowired
	private PhotoDao photoDao;
	@Transactional
	public int insertPhoto(Photo p) {
		// TODO Auto-generated method stub
		int result = photoDao.insertPhoto(p);
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
	public int deletePhoto(int photoNo) {
		int result = photoDao.deletePhoto(photoNo);
		if(result >0) {
			return result;
		}else {
			return 0;
		}
	}
}
