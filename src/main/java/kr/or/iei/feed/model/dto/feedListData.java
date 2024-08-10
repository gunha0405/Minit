package kr.or.iei.feed.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class feedListData {
	private List<Feed> list;
	private String pageNavi;
	private int totalCount;
}
