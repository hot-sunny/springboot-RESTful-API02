package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
//	// 조회
//	public List<Member> findAll() {
//		return memberRepository.findAll();
//	}
//	
//	public Member findById(Long id) {
//		return memberRepository.findById(id).orElse(null);
//	}
	
	// 생성
	public MemberResponse create(MemberRequest memberRequest) {
		var member = Member.builder()
							.name(memberRequest.getName())
							.email(memberRequest.getEmail())
							.age(memberRequest.getAge())
							.enabled(true)
							.build();
		
		memberRepository.save(member);
		
		//[ref] 별도의 mapToMemberResponse() 함수로 전환함
		//var memberResponse = MemberResponse.builder()
		//					.id(member.getId())
		//					.name(member.getName())
		//					.email(member.getEmail())
		//					.age(member.getAge())
		//					.build();
		//return memberResponse;
		
		return mapToMemberResponse(member);
	}
	
	// 생성(배치)
	//@Transactional 어노테이션: 배치 처리중 에러(unique email 중복)가 발생할 경우 이천 처리된 모든 것이 rollback 처리됨(all or nothing)
	//@Transactional 어노테이션을 붙이지 않으면 에러가 발생한 지점의 이전 처리는 정상 처리되어 DB에 반영되고 에러 지점에서 멈춤.
	@Transactional
	public List<MemberResponse> createBatch(List<MemberRequest> memberRequests) {
		return memberRequests.stream().map(this::create).toList();
	}
	
	// Mapping Member to MemberResponse
	private MemberResponse mapToMemberResponse(Member member) {
		return MemberResponse.builder()
					.id(member.getId())
					.name(member.getName())
					.email(member.getEmail())
					.age(member.getAge())
					.build();
	}
	
	// 조회
	public List<MemberResponse> findAll() {
		return memberRepository.findAll().stream().map(this::mapToMemberResponse).toList();
	}
	
	// 조회(단건)
	public MemberResponse findById(Long id) {
		var member = memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("member not found. id=" + id));
		return mapToMemberResponse(member);
	}
	
	// 수정
	public MemberResponse update(Long id, MemberRequest memberRequest) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("member not found. id=" + id));
		
		if (memberRequest.getName() != null) {
			member.setName(memberRequest.getName());
		}
		
		if (memberRequest.getEmail() != null) {
			member.setEmail(memberRequest.getEmail());
		}
		
		if (memberRequest.getAge() != null) {
			member.setAge(memberRequest.getAge());
		}
		
		memberRepository.save(member);
		
		return mapToMemberResponse(member);
	}
	
	// 삭제
	public void delete(Long id) {
		memberRepository.deleteById(id);
		return;
	}
	

}
