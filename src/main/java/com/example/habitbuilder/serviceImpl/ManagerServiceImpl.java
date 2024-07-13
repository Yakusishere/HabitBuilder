package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Manager;
import com.example.habitbuilder.mapper.ManagerMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanMapper planMapper;


    // 管理员注册
    @Override
    public void managerRegister(Manager manager){
        managerMapper.insert(manager);
    }

    //根据管理员名查询管理员信息
    @Override
    public Manager getManagerByManagerName(String managerName){

        QueryWrapper<Manager> wrapper = new QueryWrapper<Manager>();
        wrapper.eq("managerName",managerName);
        List<Manager> managers = managerMapper.selectList(wrapper);
        if(managers.isEmpty()){
            return null;
        }else{
            return managers.getFirst();
        }
    }

    //获取管理员列表
    @Override
    public List<Manager> getManagerList(){
        return managerMapper.selectList(null);
    }

    //获取用户列表
    @Override
    public List<User> getUserList(){
        return userMapper.selectList(null);
    }

    //实现搜索
    @Override
    public List<User> searchUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userName",username);
        return userMapper.selectList(queryWrapper);
    }

    //上新计划
    @Override
    public Plan addPlan(Plan plan){
        planMapper.insert(plan);
        return plan;
    }

    //下架计划
    @Override
    public void deletePlan(int planId){
        planMapper.deleteById(planId);
    }

    //更新计划
    @Override
    public void updatePlan(Plan plan){
        UpdateWrapper<Plan> oldPlan = Wrappers.update();
        oldPlan.eq("planId",plan.getPlanId());
        planMapper.update(plan,oldPlan);
    }

    @Override
    public List<Plan> managerSearchPlan(String title) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",title);
        queryWrapper.eq("userId",0); //userId为0的为模板计划
        return planMapper.selectList(queryWrapper);
    }

    @Override
    public List<Plan> getPlanList() {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",0);
        return planMapper.selectList(queryWrapper);
    }
}
