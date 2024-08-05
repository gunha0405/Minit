package kr.or.iei.photo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.photo.model.dao.PhotoDao;
import kr.or.iei.photo.model.dto.Photo;

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
}
