package kr.or.iei.feed.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.feed.model.dao.FeedDao;
import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.feed.model.dto.FeedFile;
import kr.or.iei.feed.model.dto.UserFeedNaviList;
import kr.or.iei.feed.model.dto.feedListData;
import kr.or.iei.user.model.dto.User;
import kr.or.iei.user.model.dto.UserRowMapper;

@Service
public class FeedService {
	@Autowired
	private FeedDao feedDao;

//	public List<Feed> feedList(String userId) {
//		int numPerFeedImg = 8;
//		//유저의 사진을 가져와야함
//		//유저가 올린 글의 갯수 
//		int num = feedDao.searchUserFeedNum(userId);
//		for(int i = 0; i < num ; i++) {
//			int feedNo = feedDao.searhFeedNo(userId, i);
//			List<Feed> feedFath = feedDao.searchFeed
//		}
//		
//		return ;
//	}

	public User searchUser(String userId) {
		User u = feedDao.searchUser(userId);
		return u;
	}

	@Transactional
	public int insertfile(Feed f, List<FeedFile> fileList) {
		 int result = feedDao.insertFeed(f);
	        if(result >0) {
	            //1번작업으로 insert할때 생성된 번호를 조회
	            int feedNo = feedDao.selectFeedNo();
	            //3. 반복문으로 feed_file 테이블 insert
	            for(FeedFile feedFile : fileList) {
	                //1번작업으로 insert될때 생성된 공지사항번호를 저장한 후 feed_file insert 요청
	            	feedFile.setUserFeedNo(feedNo);
	                //file_no = 0, notice_no = noticeNo, filename,filepath
	                result += feedDao.insertFeedFile(feedFile);
	            }
	        }
	        return result;
	}

	public feedListData selectUserAllFeed(int reqPage, User u) {
		//reqPage : 사용자가 요청한 페이지 번호
        //한 페이지당 보여줄 게시물의 수(지정)   -> 8개
        int numPerPage = 8;
        //사용자가 요청한 페이지에 따른 게시물 시작번호/끝번호 계산
        //reqPage == 1 -> start = 1 / end = 8
        //reqPage == 2 -> start = 9 / end = 16
        //reqPage == 3 -> start = 17 / end = 24
        //reqPage == 4 -> start = 25 / end = 32
        int end = reqPage * numPerPage; 
        int start = end -numPerPage + 1;
        
        String userId = u.getUserId();
        //요청페이지에 필요한 공지사항 목록을 조회
        List<Feed> feedList = new ArrayList<Feed>();
        
        //해당 페이지에 있는 글의 갯수 
        int num = feedDao.searchUserFeedNum(userId);
        
        //피드 갯수 만큼 for문 돌게 
        int feedNum = feedDao.selectFeedList(start, end, u);
        for(int i = 0; i < feedNum; i++) {
        	//리스트에 담을 번호 
        	int feedNo = feedDao.searhFeedNo(start, end, u , i+1);
        	String filefath = feedDao.feedFilepath(start, end, u, feedNo);
        	Feed f = new Feed();
        	f.setUserFeedFilepath(filefath);
        	f.setUserFeedNo(feedNo);
        	feedList.add(f);
        }
        
        
        //페이지 네비게이션 제작(사용자가 클릭해서 다른 페이지를 요청할 수 있도록 하는 요소 ) 제작
        //페이지 네비게이션을 서비스에서 만드는 이유 -> 총 게시물수를 조회해와야 가능하기 떄문
        //select count(*) from notice;
        int totalCount = feedDao.selectFeedTotalCount();
        //전체 페이지 수 계산
        int totalPage = 0;
        if(totalCount%numPerPage ==0) {
            totalPage = totalCount/numPerPage;
        }else {
            totalPage = totalCount/numPerPage+1;
        }
        
        //페이지 네비 사이즈 지정
        int pageNaviSize = 5;
        
        //페이지너비 시작번호를 지정
        //reqPage 1~5 : 1 2 3 4 5 
        //reqPage 6~10 : 6 7 8 9 10 
        //reqPage 11~15 : 11 12 13 14 15
        
        int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize + 1;
        //html 코드 작성(페이지 네비게이션 작성)
        String pageNavi = "<ul class='pagination circle-style'>";
        //이전버턴(1페이지로 시작하지 않으면)
        if(pageNo != 1) {
            pageNavi += "<li>";
            pageNavi += "<a class ='page-item ' href='/feed/list?reqPage="+(pageNo-1)+"'>";
            pageNavi += "<span class = 'material-icons'>chevron_left</span>";
            pageNavi += "</a></li>";
        }
        
        for(int i =0;i<pageNaviSize;i++) {
            pageNavi += "<li>";
            if(pageNo==reqPage) {
                pageNavi += "<a class ='page-item active-page' href='/feed/myPage?reqPage="+pageNo+"'>";
                
            }else {
                pageNavi += "<a class ='page-item' href='/feed/list?myPage="+pageNo+"'>";
                
            }
            pageNavi += pageNo;
            pageNavi += "</a></li>";
            pageNo++;
            //페이지를 만들다가 최종페이지를 출력했으면 더이상 반복하지 않고 종료
            if(pageNo>totalPage) {
                break;
            }
        }
        //다음버튼 생성(최종 페이지를 출력하지 않았으면)
        if(pageNo <= totalPage) {
            pageNavi += "<li>";
            pageNavi += "<a class ='page-item ' href='/feed/list?reqPage="+pageNo+"'>";
            pageNavi += "<span class = 'material-icons'>chevron_right</span>";
            pageNavi += "</a></li>";
        }
        pageNavi +="</ul>";
        //컨트롤러로 되돌려줄 데이터가 공지사항 목록, 만든 페이지 네비게이션
        //-> java의 메소드는 1개의 자료형만 리턴 가능 -> 2개를 되돌려줘야함 List, String
        //-> 되돌려주고 싶은 데이터 2개를 저장할 수 있는 객체를 생성해서 객체로 묶어서 하나로 리턴
        feedListData fld = new feedListData(feedList,pageNavi);
        return fld;
	}


}
