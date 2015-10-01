package com.example.androidparsexml;

public class ParsedDataSet {

	private String parentTag = null;
	private String name = null;
	private String age = null;
	private String emailAddress = null;
	private String birthday = null;

	// parent tag
	public String getParentTag() {
		return parentTag;
	}

	public void setParentTag(String parentTag) {
		this.parentTag = parentTag;
	}

	// name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// age
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	// emailAddress
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	// birthday
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String name) {
		this.birthday = name;
	}
}