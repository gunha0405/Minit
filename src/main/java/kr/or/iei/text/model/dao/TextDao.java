package kr.or.iei.text.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.iei.text.model.dto.TextRowMapper;

@Repository
public class TextDao {

	@Autowired
	private TextRowMapper textRowMapper;
}
