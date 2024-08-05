package kr.or.iei.text.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.text.model.dao.TextDao;

@Service
public class TextService {
	@Autowired
	private TextDao textDao;

	public List selectTextFeed() {
		List list = textDao.selectTextFeed();
		return list;
	}
}
