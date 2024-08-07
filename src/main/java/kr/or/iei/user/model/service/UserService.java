package kr.or.iei.user.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.user.model.dao.UserDao;
import kr.or.iei.user.model.dao.UserImgDao;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserImg;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserImgDao userImgDao;
	
	public User selectOneUser(User u) {
		User user = userDao.selectOneUser(u);
		return user;
	}

	public User selectOneUser(String userNick) {
		User user = userDao.selectOneUser(userNick);
		return user;
	}

	@Transactional
	public int insertUser(User u) {
		int result = userDao.insertUser(u);
		return result;
	}

	@Transactional
	public int updateUser(User u) {
		int result = userDao.updateUser(u);
		return result;
	}

	public int deleteUser(User user) {
		int result = userDao.deleteUser(user);
		/*
		UserImg userImgParam = new UserImg();
		userImgParam.setUserId(user.getUserId());
		userImgDao.deleteUserImg(userImgParam);
		*/
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public User selectUser(User user, UserImg userImg) {
		User getUser = userDao.selectUser(user);
		if(getUser != null && userImg != null) {
			List<UserImg> userImgList = userImgDao.selectUserImg(userImg);
			getUser.setImgList(userImgList);
		}
		return getUser;
	}
	
	//로그인시 사용
	public User selectUser(User user) {
		User getUser = userDao.selectUser(user);
		return getUser;
	}
	
	@Transactional
	public int insertUser(User user) {
		int result = userDao.insertUser(user);
		return result;
	}
	
	@Transactional
//	public int updateUser(User user, List<UserImg> userImgList, int[] delImgNo) {
	public int updateUser(User user) {
		int result = userDao.updateUser(user);
		
//		if(result > 0) {
//			for(UserImg userImg : userImgList) {
//				result += userImgDao.insertUserImg(userImg);
//				if(delImgNo != null) {
//					for(int imgNo : delImgNo) {
//						UserImg userImgParam = new UserImg();
//						userImgParam.setImgNo(imgNo);
//						userImgParam.setUserId(user.getUserId());
//						result += userImgDao.deleteUserImg(userImgParam);
//					}
//				}
//			}
//		}
		
		return result;
	}
	
	@Transactional
	public int deleteUser(User user) {
		int result = userDao.deleteUser(user);

		UserImg userImgParam = new UserImg();
		userImgParam.setUserId(user.getUserId());
		userImgDao.deleteUserImg(userImgParam);
		return result;
	}
*/
	
	
}