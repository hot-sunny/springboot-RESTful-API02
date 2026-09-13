package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "TBL_MEMBER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String email;       //[JPA] auto-ddl 기능에 의해 자동으로 테이블 생성시 unique 컬럼 속성을 세팅함. 만약 이미 만들어져 있는 테이블이라면 이 unique=true 속성은 무시됨.
	private Integer age;
	
	private String password;	//[내부관리필드]
	private Boolean enabled;	//[내부관리필드] 계정이 활성화 되어 있는지 여부
}
