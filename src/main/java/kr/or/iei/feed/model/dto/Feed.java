package kr.or.iei.feed.model.dto;

import java.util.List;

import kr.or.iei.photo.model.dto.Photo;
import kr.or.iei.text.model.dto.TextFeed;
import kr.or.iei.user.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feed {
	private int userFeedNo;
	private String userFeedWriter;
	private String userFeedContent;
	private String userFeedDate;
	private int userFeedCount;
	private User user;
	private List<FeedFile> feedList;
	private String userFeedFilepath;
	private String file1;
	private String file2;
	private String file3;
	private List<FeedComment> feedComment;
	private List<User> userList;
	private int isLike;
	private int isReport;
	private int isRepository;
	private int feedLikeCount;
	private int userNo;
	private List<Photo> photoList;
	private List<TextFeed> textList;
	private List<Feed> userFeedList;
}
