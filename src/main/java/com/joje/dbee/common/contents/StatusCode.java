package com.joje.dbee.common.contents;

public enum StatusCode {

	SUCCESS(100, "요청 완료"),
	FAILED_ERROR(200, "요청 실패"),
	BAD_REQUEST(201, "잘못된 파라미터 값"),
	FAILED_NO_DATA(202, "해당 정보를 찾을 수 없습니다."),
	FAILED_CONNECT(203, "타 서버간 요청이 원활하지 않습니다."),
	DUPLICATE_DATA(204, "중복된 데이터 입니다."),
	JWT_EXPIRED(205, "만료된 JWT 토큰"),
	FORBIDDEN(403, "해당 요청에 권한이 없습니다."),
	INTERNAL_SERVER_ERROR(500, "서버 에러");
	
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	StatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
