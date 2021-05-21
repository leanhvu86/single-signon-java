package com.tsolution.sso._1entities.enums;

public enum LoginStatus {
	FAILED("FAILED"), SUCCESS("SUCCESS");

	private String value;

	LoginStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
