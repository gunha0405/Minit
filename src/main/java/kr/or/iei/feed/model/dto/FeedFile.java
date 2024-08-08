package kr.or.iei.feed.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedFile {
	private int userFeedFileNo;
	private int userFeedNo; //유저 넘버
	private String userFeedFilepath;
	private List<FeedFile> fils;

}
