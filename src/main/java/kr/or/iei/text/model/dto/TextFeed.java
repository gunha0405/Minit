package kr.or.iei.text.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TextFeed {
	private int textFeedNo;
	private String textFeedContent;
	private String textFeedRegDate;
	private String textFeedWriter;
	private List<TextFeedComment> textFeedCommentList;
}
