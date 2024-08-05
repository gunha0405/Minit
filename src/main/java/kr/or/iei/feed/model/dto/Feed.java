package kr.or.iei.feed.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feed {
	private int userFeedNo;
	private String userFeedWriter;
	private String userFeedContnet;
	private String userFeedDate;
	private int userFeedCount;
}