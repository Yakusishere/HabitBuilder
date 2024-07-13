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

    List<Object[]> dailyPlanType(int userId,LocalDate date);

    Integer findUserIdByPlanId(int planId);

    void addPlan(Plan plan);

    void deletePlan(int planId);

    void updatePlan(Plan plan);

    void autoAddPlan(Plan plan);

    List<Plan> getMyPlan(int userId);

    int lowerScore(int userId,LocalDate date);

    List<Plan> searchPlan(String title);

    List<Plan> getPlanList();

    String[] fixPlan(Plan plan,String request);

    void completeFix(int planId, String[] planContent);
}
