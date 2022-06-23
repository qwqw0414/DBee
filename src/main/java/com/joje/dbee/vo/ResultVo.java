package com.joje.dbee.vo;

import java.util.HashMap;
import java.util.Map;

import com.joje.dbee.common.contents.StatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ResultVo {

	private StatusCode status;
	private int code;
	private Map<String, Object> data;
	
	public ResultVo() {
		this.status = StatusCode.SUCCESS;
		this.code = StatusCode.SUCCESS.getCode();
		this.data = new HashMap<>();
	}
	
	public ResultVo(StatusCode status) {
		this.status = status;
		this.code = status.getCode();
		this.data = new HashMap<>();
	}
	
	public void put(String key, Object obj) {
		data.put(key, obj);
	}
	
}
