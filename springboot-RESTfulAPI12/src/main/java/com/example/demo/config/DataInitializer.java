package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Component
@Profile({"dev","test"})
public class DataInitializer implements ApplicationRunner {
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var member1 = Member.builder()
						.name("홍길동")
						.email("kildongHong@korea.com")
						.age(25)
						.enabled(true).build();
		
		memberRepository.save(member1);
		
		var member2 = Member.builder()
						.name("박문수")
						.email("moonsuPark@korea.com")
						.age(30)
						.enabled(true).build();
		
		memberRepository.save(member2);
		
	}

}
