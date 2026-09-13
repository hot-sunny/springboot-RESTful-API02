package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.MemberService;

/**
(1) RESTful API 설계 예
* 메서드 타입(method type): 하고자 하는 목적을 '메서드 타입' 나눠서 규격을 정합
  - 조회(GET), 입력(POST), 수정(PUT), 삭제(DELETE)

예) '무엇'에 대해서 '어떤 작업'을 하고 싶은 것이냐?
    - '무엇' -> '리소스 URL' 로 표현
    - '어떤 작업' -> '메소드 type(GET, POST, PUT, DELETE)' 으로 해당 동작을 표현함

  1. 잘못된 설계
    /addMember
    /member/add
    /delete/member/32
    
  2. 올바른 설계
  --------------------------------------------------------------------------
    작업구분    메서드    URL                    설명
  --------------------------------------------------------------------------
    조회(전체)   GET     /members              모든 회원 목록을 조회
    조회(상세)   GET     /members/21           회원 ID 가 21 에 해당하는 회원 정보 조회
    조회(상세)   GET     /members/21/articles  회원 ID 가 21 에 해당하는 회원이 작성한 게시글 목록 조회
      
    생성         POST    /members              전달된 정보를 사용하여 회원 정보 생성
    수정         PUT     /members/21           전달된 정보를 사용하여 회원 ID가 21 인 회원 정보 수정 
    삭제         DELETE  /members/21           회원 ID 가 21 에 해당하는 회원 정보 삭제 
  --------------------------------------------------------------------------
  
  3. 파라미터 전달
    GET /members?name=홍길동    -- name 이 "홍길동"인 회원 조회 (@RequestParam("name") 을 사용하는 경우)
    GET /members?sort=name      -- name 순으로 회원 목록 조회
    
    POST   /members/21     -- ID 가 21 멤버 정보 수정(전체) (@PathVariable("id"), @RequestBody 를 사용하는 경우)
    PATCH  /members/21     -- ID 가 21 멤버 정보 수정(부분) (@PathVariable("id"), @RequestBody 를 사용하는 경우)
    DELETE /members/21     -- ID 가 21 멤버 정보 삭제 (@PathVariable("id")

 */

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
//	// 조회
//	@GetMapping()
//	public List<Member> get() {
//		return memberService.findAll();
//	}
//	// 조회(단건)
//	@GetMapping("/{id}")
//	public Member getById(@PathVariable("id") Long id) {
//		return memberService.findById(id);
//	}
	
//	// 생성 (단건)
//	@PostMapping()
//	public MemberResponse post(@RequestBody MemberRequest memberRequest) {
//		return memberService.create(memberRequest);
//	}
	
	// 생성 (배치)
	@PostMapping()
	public List<MemberResponse> postBatch(@RequestBody List<MemberRequest> memberRequests) {
		return memberService.createBatch(memberRequests);
	}
	
	// 조회
	@GetMapping()
	public List<MemberResponse> get() {
		return memberService.findAll();
	}
	
	// 조회(단건)
	@GetMapping("/{id}")
	public MemberResponse getById(@PathVariable("id") Long id) {
		return memberService.findById(id);
	}
	
	// 수정(전체)
	@PutMapping("/{id}")
	public MemberResponse put(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
		return memberService.update(id, memberRequest);
	}
	
	// 수정(부분)
	@PatchMapping("/{id}")
	public MemberResponse patch(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
		return memberService.update(id, memberRequest);
	}
	
	// 삭제
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		memberService.delete(id);
		return;
	}

}
