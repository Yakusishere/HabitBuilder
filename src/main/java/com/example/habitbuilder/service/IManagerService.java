package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IManagerService extends IService<Manager>, UserDetailsService {
    // 管理员注册
    void managerRegister(Manager manager);

    //根据管理员名查询管理员信息
    Manager getManagerByManagerName(String managerName);

    //获取管理员列表
    List<Manager> getManagerList();

    //获取用户列表
    List<User> getUserList();

    //上新计划
    Plan addPlan(Plan plan);

    //下架计划
    void deletePlan(int planId);

    //更新计划
    void updatePlan(Plan plan);

    List<User> searchUser(String username);

    List<Plan> managerSearchPlan(String title);

    List<Plan> getPlanList();

    int getUserCount();

    int getActiveUserCount();

    int getUsersWithPostsOnly();

    int getUsersWithPlansOnly();

    int getUsersWithBothPostsAndPlans();

    int getTotalPostCount();

    int[] getPlanCountByTag();

    int getTotalPlanCount();

    int getTotalConversationCount();

    int getTotalEventCount();

    double getAveragePlanCount();

    double getAveragePostCount();

    double getAverageConversationCount();

    double getAverageEventCount();
}
