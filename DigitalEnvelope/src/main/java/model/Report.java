package model;

import java.io.Serializable;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uniqueCode; // 고유 코드
	private String reportContent; // 신고 내용 (원문)
	private byte[] encryptedEnvelope; // 전자봉투 (ex. 암호화된 데이터)
	private boolean isVerified; // 검증 여부

	public Report(String uniqueCode, String reportContent, byte[] encryptedEnvelope) {
		this.uniqueCode = uniqueCode;
		this.reportContent = reportContent;
		this.encryptedEnvelope = encryptedEnvelope;
		this.isVerified = false; // 초기에는 검증 전(false)
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public byte[] getEncryptedEnvelope() {
		return encryptedEnvelope;
	}

	public void setEncryptedEnvelope(byte[] encryptedEnvelope) {
		this.encryptedEnvelope = encryptedEnvelope;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		this.isVerified = verified;
	}
}
