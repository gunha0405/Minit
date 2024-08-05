package kr.or.iei.text.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.text.model.dao.TextDao;

@Service
public class TextService {
	@Autowired
	private TextDao textDao;
}
