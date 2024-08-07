package kr.or.iei.feed.model.dto;

import java.util.List;


import kr.or.iei.user.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFeedNaviList {
	private User user;
	private List<Feed> feedList;
}
