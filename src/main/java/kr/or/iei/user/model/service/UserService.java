package kr.or.iei.user.model.service;

import java.util.List;
import java.util.StringTokenizer;

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

	public User selectUserNick(String userNick) {
		User user = userDao.selectUserNick(userNick);
		return user;
	}
	
	public User selectUserId(String userId) {
		User user = userDao.selectUserId(userId);
		return user;
	}

	public User selectUserEmail(String receiver) {
		User user = userDao.selectUserEmail(receiver);
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
	
	@Transactional
	public int deleteUser(User user) {
		int result = userDao.deleteUser(user);
		/*
		UserImg userImgParam = new UserImg();
		userImgParam.setUserId(user.getUserId());
		userImgDao.deleteUserImg(userImgParam);
		*/
		return result;
	}

	public User searchUserId(User u) {
		User user = userDao.searchUserId(u);
		return user;
	}

	public User ajaxFindUser(String userId, String userEmail) {
		User user = userDao.ajaxFindUser(userId, userEmail);
		return user;
	}

	@Transactional
	public int updatePw(User u) {
		int result = userDao.updatePw(u);
		return result;
	}

	public List selectAllUser() {
		List list = userDao.selectAllUser();
		return list;
	}

	@Transactional
	public int changeCount(User u) {
		int result = userDao.changeCount(u);
		return result;
	}

	public boolean checkedChangeCount(String no, String count) {
		StringTokenizer sTno = new StringTokenizer(no, "/");
		StringTokenizer sTcount = new StringTokenizer(count, "/");
		boolean result = true;
		while(sTno.hasMoreTokens()) {
			int userNo = Integer.parseInt(sTno.nextToken());
			int warningCount = Integer.parseInt(sTcount.nextToken());
			User u = new User();
			u.setUserNo(userNo);
			u.setWarningCount(warningCount);
			int intResult = userDao.changeCount(u);
			if(intResult == 0) {
				result = false;
				break;
			}
		}
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