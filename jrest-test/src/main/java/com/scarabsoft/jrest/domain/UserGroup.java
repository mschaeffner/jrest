package com.scarabsoft.jrest.domain;

public class UserGroup {

	private final int userId;
	private final int groupId;

	public UserGroup(int userId, int groupId) {
		this.userId = userId;
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public int getGroupId() {
		return groupId;
	}

}
