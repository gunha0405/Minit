package kr.or.iei.photo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo {
	private int photoFeedNo;
	private String photoFeedImg;
	private String photoFeedWriter;
	private int readCount;
	private String regDate;
}
