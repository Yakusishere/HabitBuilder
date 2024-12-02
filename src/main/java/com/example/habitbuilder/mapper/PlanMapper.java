package com.example.habitbuilder.mapper;

import com.example.habitbuilder.domain.vo.PlanOptionVo;
import com.example.habitbuilder.pojo.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface PlanMapper extends BaseMapper<Plan> {
	List<PlanOptionVo> getDailyPlanOptions(int userId, LocalDate date);
}
