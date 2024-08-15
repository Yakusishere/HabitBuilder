package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.BrowseHistoryVo;
import com.example.habitbuilder.domain.vo.PostOverviewVo;
import com.example.habitbuilder.pojo.Browsepost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-30
 */
public interface IBrowsepostService extends IService<Browsepost> {
	List<BrowseHistoryVo> getBrowseHistory(String token);

	void startBrowsing(String token, int postId);

	void endBrowsing(String token, int postId);
}
