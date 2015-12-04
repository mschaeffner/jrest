package com.scibee.freya.test.domain;


public final class IP {

	private String ip;
	private long requestDate;
	private String country;

	private IP() {
	}

	public String getIp() {
		return ip;
	}

	public long getRequestDate() {
		return requestDate;
	}

	public String getCountry() {
		return country;
	}

	public static final class Builder {
		private final IP buildResult = new IP();

		public IP build() {
			return buildResult;
		}

		public Builder ip(String ip) {
			buildResult.ip = ip;
			return this;
		}

		public Builder requestDate(long date) {
			buildResult.requestDate = date;
			return this;
		}

		public Builder getCountry(String country) {
			buildResult.country = country;
			return this;
		}
	}
}
