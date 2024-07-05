package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Plan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IPlanService extends IService<Plan> {

    List<String> dailyPlanType(LocalDate date);
}
