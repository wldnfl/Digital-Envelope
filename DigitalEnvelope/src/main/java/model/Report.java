package model;

import java.io.Serializable;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uniqueCode; // 고유 코드
	private String reportContent; // 신고 내용 (원문)
	// 전자봉투 암호문과 대칭키를 Base64 문자열로 저장
	private String encryptedDocumentBase64;
	private String encryptedSecretKeyBase64;
	private boolean isVerified; // 검증 여부

	public Report(String uniqueCode, String reportContent, String encryptedDocumentBase64,
			String encryptedSecretKeyBase64) {
		this.uniqueCode = uniqueCode;
		this.reportContent = reportContent;
		this.encryptedDocumentBase64 = encryptedDocumentBase64;
		this.encryptedSecretKeyBase64 = encryptedSecretKeyBase64;
		this.isVerified = false;
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

	public String getEncryptedDocumentBase64() {
		return encryptedDocumentBase64;
	}

	public String getEncryptedSecretKeyBase64() {
		return encryptedSecretKeyBase64;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		this.isVerified = verified;
	}
}
