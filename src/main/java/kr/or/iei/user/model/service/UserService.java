package kr.or.iei.user.model.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dao.UserDao;
import kr.or.iei.user.model.dto.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
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

	public User searchDelUserEmail(String receiver) {
		User user = userDao.searchDelUserEmail(receiver);
		return user;
	}
	
	@Transactional
	public int insertUser(User u) {
		int result = userDao.insertUser(u);
		return result;
	}

	/*
	public int selectUserNo(String userId) {
		int userNo = userDao.selectUserNo(userId);
		return userNo;
	}
	
	@Transactional
	public int insertUserImg(int userNo) {
		int result = userImgDao.insertUserImg(userNo);
		return result;
	}
	*/

	@Transactional
	public int updateUserAll(User u) {
		int result = userDao.updateUserAll(u);
		System.out.println(u);
		return result;
	}
	@Transactional
	public int updateUserBasic(User u) {
		int result = userDao.updateUserBasic(u);
		System.out.println(u);
		return result;
	}
	@Transactional
	public int updateUserReturn(User u) {
		int result = userDao.updateUserReturn(u);
		System.out.println(u);
		return result;
	}
	
	@Transactional
	public int deleteUser(User user) {
		int result = userDao.delInsertUser(user);
		result += userDao.deleteUser(user);
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

	public User selectOneUser(int userNo) {
		User u = userDao.selectOneUser(userNo);
		return u;
	}
	
	@Transactional
	public int textChangeCount(User u, int textFeedNo) {
		int result = userDao.changeCount(u); //경고 횟수 누적
		result += userDao.deleteTextFeed(textFeedNo); //해당 피드 삭제
		int warningCount = userDao.warningCount(u); //해당 회원의 경고횟수 받아오기
		if(warningCount == 5) {
			result++;
			result += userDao.updateLevel(u);
		}
		return result;
	}
	@Transactional
	public int photoChangeCount(User u, int photoFeedNo) {
		int result = userDao.changeCount(u); //경고 횟수 누적
		result += userDao.deletePhotoFeed(photoFeedNo); //해당 피드 삭제
		int warningCount = userDao.warningCount(u); //해당 회원의 경고횟수 받아오기
		if(warningCount == 5) {
			result++;
			result += userDao.updateLevel(u);
		}
		return result;
	}


	/*
	@Transactional
	public int checkedChangeCount(String no, String count) {
		StringTokenizer sTno = new StringTokenizer(no, "/");
		StringTokenizer sTcount = new StringTokenizer(count, "/");
		int result = 0;
		int result1 = 0;
		int result2 = 0;
		while(sTno.hasMoreTokens()) {
			int userNo = Integer.parseInt(sTno.nextToken());
			int warnCount = Integer.parseInt(sTcount.nextToken());
			User u = new User();
			u.setUserNo(userNo);
			u.setWarningCount(warnCount);
			result1 += userDao.changeCount(u);
			int warningCount = userDao.warningCount(u);
			if(warningCount == 5) {
				result2 += userDao.updateLevel(u);
			}
		}if(result1 != 0 && result2 !=0) {
			result += 2;
		}else if(result1 != 0 && result2 == 0) {
			result += 1;
		}else {
			result = 0;
		}
		return result;
	}
	/*
	@Transactional
	public boolean checkedChangeCount(String no, String count) {
		StringTokenizer sTno = new StringTokenizer(no, "/");
		StringTokenizer sTcount = new StringTokenizer(count, "/");
		boolean result = true;
		while(sTno.hasMoreTokens()) {
			int userNo = Integer.parseInt(sTno.nextToken());
			int warnCount = Integer.parseInt(sTcount.nextToken());
			User u = new User();
			u.setUserNo(userNo);
			u.setWarningCount(warnCount);
			int intResult = userDao.changeCount(u);
			int warningCount = userDao.warningCount(u);
			if(warningCount == 5) {
				intResult += userDao.updateLevel(u);
			}
			if(intResult == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
	*/
	
}