package com.joje.dbee.vo.common;

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
	private Map<String, Object> data;
	
	public ResultVo() {
		this.status = StatusCode.SUCCESS;
		this.data = new HashMap<>();
	}
	
	public void put(String key, Object obj) {
		data.put(key, obj);
	}
	
}
