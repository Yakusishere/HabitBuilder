package com.example.habitbuilder.serviceImpl;

import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.Qwen2;
import com.example.habitbuilder.mapper.EventMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements IPlanService {
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public List<Object[]> dailyPlanType(int userId,LocalDate date) {
        List<Integer> planIds = eventService.findPlanIdByDate(date);
        if (planIds.isEmpty()) {
            return List.of();
        }

        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.in("planId", planIds).eq("userId",userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);

        List<Object[]> result = new ArrayList<>();
        for (Plan plan : plans) {
            result.add(new Object[]{plan.getPlanId(), plan.getTitle()});
        }

        return result;
    }
    public Integer findUserIdByPlanId(int planId){
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("userId").eq("planId", planId);
        Plan plan = planMapper.selectOne(queryWrapper);
        return plan != null ? plan.getUserId() : null;
    }
    public void addPlan(Plan plan) {
        planMapper.insert(plan);
    }

    public void deletePlan(int planId) {
        planMapper.deleteById(planId);
    }

    public void updatePlan(Plan plan) {

        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("planId", plan.getPlanId());
        planMapper.update(plan, wrapper);
    }

    public List<Plan> getMyPlan(int userId) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        return planMapper.selectList(wrapper);
    }

    @Override
    public int lowerScore(int userId,LocalDate date) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        List<Plan>plans=planMapper.selectList(wrapper);
        boolean flag=true;
        User user= userServiceImpl.getUserId(userId);
        for (int i = 0; i < plans.size(); i++) {
            Plan plan= plans.get(i);
            System.out.println(plan.getPlanId());
            QueryWrapper<Event> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("planId", plan.getPlanId());
            List<Event>events=eventMapper.selectList(wrapper1);
            for (int j = 0; j < events.size(); j++) {
                Event event=events.get(j);
                if(event.getExecutionDate().equals(date) && (event.getIsCompleted()==false)){
                    flag=false;
                    user.setMyScore(user.getMyScore()-2);
                    System.out.println(user.getMyScore());
                }
            }
        }
        if(flag==false){
            userServiceImpl.updateById(user);
            return user.getMyScore();
        }else{
            return -1;
        }
    }

    @Override
    public List<Plan> searchPlan(String title) {
        QueryWrapper <Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        return planMapper.selectList(queryWrapper);
    }

    public void autoAddPlan(Plan plan) {
        plan.setStartDate(LocalDate.from(plan.getCreateDate().plusDays(1)));
        plan.setEndDate(LocalDate.from(plan.getCreateDate().plusDays(7)));

        planMapper.insert(plan); //先生成一个计划
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planId",plan.getPlanId());
        Plan newPlan = planMapper.selectOne(queryWrapper);
        LocalDate startDate = newPlan.getStartDate();  //从计划开始的日期开始一周
        try {
            String[] events = Qwen2.streamCallWithMessage(newPlan.getTitle(),newPlan.getDescription()); // 或者这个计划的所有事件
            for(String event : events){
                //event的起始时间设置重新考虑
                Event eventDay = new Event(0,newPlan.getPlanId(),event,startDate.plusDays(1), LocalTime.of(0, 0, 0),LocalTime.of(0, 0, 0),false);
                eventService.save(eventDay); // 存入数据库 这个方法会先检查数据库是否有这个值，没有则插入，有则更新
            }
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage()); //报错处理 gpt输出格式有问题
    }
        }
}
