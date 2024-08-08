package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.Manager;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ConversationMapper conversationMapper;
    @Autowired
    private EventMapper eventMapper;


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

    @Override
    public int getUserCount() {
        return Math.toIntExact(userMapper.selectCount(null));
    }

    @Override
    public int getActiveUserCount() {
        // 获取创建过计划的用户ID列表
        QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
        planWrapper.select("DISTINCT userId");
        Set<Integer> activeUserIds = new HashSet<>();
        planMapper.selectList(planWrapper).forEach(plan -> activeUserIds.add(plan.getUserId()));

        // 获取发布过帖子的用户ID列表
        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.select("DISTINCT userId");
        postMapper.selectList(postWrapper).forEach(post -> activeUserIds.add(post.getUserId()));

        return activeUserIds.size();
    }

    @Override
    public int getUsersWithPostsOnly() {
        // 获取创建过计划的用户ID列表
        QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
        planWrapper.select("DISTINCT userId");
        Set<Integer> planUserIds = new HashSet<>();
        planMapper.selectList(planWrapper).forEach(plan -> planUserIds.add(plan.getUserId()));

        // 获取发布过帖子的用户ID列表
        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.select("DISTINCT userId");
        Set<Integer> postUserIds = new HashSet<>();
        postMapper.selectList(postWrapper).forEach(post -> postUserIds.add(post.getUserId()));

        // 计算只发布帖子但没有创建过计划的用户数
        postUserIds.removeAll(planUserIds);
        return postUserIds.size();
    }

    @Override
    public int getUsersWithPlansOnly() {
        // 获取创建过计划的用户ID列表
        QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
        planWrapper.select("DISTINCT userId");
        Set<Integer> planUserIds = new HashSet<>();
        planMapper.selectList(planWrapper).forEach(plan -> planUserIds.add(plan.getUserId()));

        // 获取发布过帖子的用户ID列表
        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.select("DISTINCT userId");
        Set<Integer> postUserIds = new HashSet<>();
        postMapper.selectList(postWrapper).forEach(post -> postUserIds.add(post.getUserId()));

        // 计算只创建过计划但没有发布过帖子的用户数
        planUserIds.removeAll(postUserIds);
        return planUserIds.size();
    }

    @Override
    public int getUsersWithBothPostsAndPlans() {
        // 获取创建过计划的用户ID列表
        QueryWrapper<Plan> planWrapper = new QueryWrapper<>();
        planWrapper.select("DISTINCT userId");
        Set<Integer> planUserIds = new HashSet<>();
        planMapper.selectList(planWrapper).forEach(plan -> planUserIds.add(plan.getUserId()));

        // 获取发布过帖子的用户ID列表
        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.select("DISTINCT userId");
        Set<Integer> postUserIds = new HashSet<>();
        postMapper.selectList(postWrapper).forEach(post -> postUserIds.add(post.getUserId()));

        // 计算既发布过帖子又创建过计划的用户数
        planUserIds.retainAll(postUserIds);
        return planUserIds.size();
    }

    @Override
    public int[] getPlanCountByTag() {
        String[] tags = {"exercise", "study", "change", "life", "relax", "finance"};
        int[] counts = new int[tags.length];

        for (int i = 0; i < tags.length; i++) {
            QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tag", tags[i]);
            counts[i] = Math.toIntExact(planMapper.selectCount(queryWrapper));
        }

        return counts;
    }

    @Override
    public int getTotalPostCount() {
        return Math.toIntExact(postMapper.selectCount(null));
    }

    @Override
    public int getTotalPlanCount() {
        return Math.toIntExact(planMapper.selectCount(null));
    }

    @Override
    public int getTotalConversationCount() {
        return Math.toIntExact(conversationMapper.selectCount(null));
    }

    @Override
    public int getTotalEventCount() {
        return Math.toIntExact(eventMapper.selectCount(null));
    }

    @Override
    public double getAveragePlanCount() {
        int totalPlanCount = getTotalPlanCount();
        int userCount = getUserCount();
        return userCount == 0 ? 0 : (double) totalPlanCount / userCount;
    }

    @Override
    public double getAveragePostCount() {
        int totalPostCount = getTotalPostCount();
        int userCount = getUserCount();
        return userCount == 0 ? 0 : (double) totalPostCount / userCount;
    }

    @Override
    public double getAverageConversationCount() {
        int totalConversationCount = getTotalConversationCount();
        int userCount = getUserCount();
        return userCount == 0 ? 0 : (double) totalConversationCount / userCount;
    }

    @Override
    public double getAverageEventCount() {
        int totalEventCount = getTotalEventCount();
        int userCount = getUserCount();
        return userCount == 0 ? 0 : (double) totalEventCount / userCount;
    }
}
