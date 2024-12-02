package com.example.habitbuilder.mapper;

import com.example.habitbuilder.domain.bo.EventBo;
import com.example.habitbuilder.domain.vo.EventVo;
import com.example.habitbuilder.pojo.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface EventMapper extends BaseMapper<Event> {
	List<EventVo> getEventList(EventBo bo);
}
