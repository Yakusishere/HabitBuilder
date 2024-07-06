package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Override
    public List<Object[]> dailyPlanType(LocalDate date) {
        List<Integer> planIds = eventService.findPlanIdByDate(date);
        if (planIds.isEmpty()) {
            return List.of();
        }

        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.in("planId", planIds);
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
}
