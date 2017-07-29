package com.perficient.hr.form;

public class VersionDetails {

	private String version;
	
	private String minSupportedVersion;

	private String message;

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the minSupportedVersion
	 */
	public String getMinSupportedVersion() {
		return minSupportedVersion;
	}

	/**
	 * @param minSupportedVersion the minSupportedVersion to set
	 */
	public void setMinSupportedVersion(String minSupportedVersion) {
		this.minSupportedVersion = minSupportedVersion;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
