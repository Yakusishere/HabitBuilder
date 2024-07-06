package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IManagerService extends IService<Manager> {
    // 管理员注册
    void managerRegister(Manager manager);

    //根据管理员名查询管理员信息
    Manager getManagerByManagerName(String managerName);

    //获取管理员列表
    List<Manager> getManagerList();

    //获取用户列表
    List<User> getUserList();

    //上新计划
    void addPlan(Plan plan);

    //下架计划
    void deletePlan(int planId);

    //更新计划
    void updatePlan(Plan plan);

}
