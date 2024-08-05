package kr.or.iei.text.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Text {
	private int textFeedNo;
	private String textFeedContent;
	private int textFeedReadCount;
	private String textFeedRegDate;
	private String textFeedWriter;
}
