package com.example.habitbuilder.mapper;

import com.example.habitbuilder.domain.vo.BrowseHistoryVo;
import com.example.habitbuilder.pojo.Browsepost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.habitbuilder.pojo.Post;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-30
 */
public interface BrowsepostMapper extends BaseMapper<Browsepost> {
	List<BrowseHistoryVo> selectBrowseList(Integer userId);
}
