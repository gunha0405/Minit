package kr.or.iei.text.model.dto;

import kr.or.iei.user.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TextFeedCommentJsonList {
	private User user;
	private TextFeedComment textFeedComment;
}
