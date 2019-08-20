package com.rmpksoft.qrtz.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Override
	public Set<String> getStudentNameList() {
		Set<String> s = new HashSet<>();
		s.add("AAAA");
		s.add("BBBB");
		s.add("CCCC");
		s.add("DDDD");
		s.add("EEEE");
		return s;
	}

}
