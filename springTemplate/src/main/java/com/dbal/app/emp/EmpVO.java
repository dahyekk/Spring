package com.dbal.app.emp;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //Bean생성자를 만들어줌
public class EmpVO {
	@JsonProperty(value="id")
	String employeeId;
	String firstName;
	String lastName;
	String email;
	String msg;
	
	MultipartFile uploadFile;
	String profile;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	String hireDate;
	
	@JsonIgnore String jobId;
	@JsonIgnore String departmentId;
	@JsonIgnore Integer salary;
	@JsonIgnore Integer[] employeeIds;
}