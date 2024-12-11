package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.PlanOptionVo;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.pojo.Plan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IPlanService extends IService<Plan> {

    List<PlanOptionVo> dailyPlanOptions(String token, LocalDate date);

    Integer findUserIdByPlanId(int planId);

    Plan addPlan(String token, Plan plan);

    void deletePlan(int planId);

    void updatePlan(Plan plan);

    HistoryConversation autoAddPlan(Plan plan);

    List<Plan> getMyPlan(int userId);

    int lowerScore(int userId, LocalDate date);

    List<Plan> searchPlan(String title, String tag, int userId);

    List<Plan> getPlanList();

    String[] fixPlan(Integer planId, String request);

    void completeFix(int planId, String[] planContent);

    Object searchByTag(String tag, int userId);
}
