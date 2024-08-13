package com.example.habitbuilder.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.*;
import java.util.Objects;

@Data
public class PageQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 页码
	 */
	private Integer pageNum;

	/**
	 * 页面大小
	 */
	private Integer pageSize;

	/**
	 * 创建分页查询
	 *
	 * @return {@link Page }<{@link T }>
	 */
	public <T> Page<T> build() {
		if (pageNum == null) pageNum = 1;
		if (pageSize == null) pageSize = 0x7FFFFFFF;
		Page<T> page = new Page<>(pageNum, pageSize);
		return page;
	}
}
