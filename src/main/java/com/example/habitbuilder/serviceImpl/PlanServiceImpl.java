package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.service.IPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<String> dailyPlanType(LocalDate date) {
        List<Integer> planIds=eventService.findPlanIdByDate(date);
        if(planIds.isEmpty()){
            return List.of();
        }
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.in("planId", planIds);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        return plans.stream()
                .map(Plan::getTitle)
                .collect(Collectors.toList());
    }
}
