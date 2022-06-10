package com.joje.dbee.vo.common;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaginationVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int page = 1;
	private int numPerPage = 5;
	private int totalContents;
	private int pageBarSize = 5;

	public PaginationVo(int cPage, int totalContents) {
		this.page = cPage;
		this.totalContents = totalContents;
	}

	public RowBounds getRowBounds() {
		return new RowBounds((this.page - 1) * this.numPerPage, this.numPerPage);
	}
}
