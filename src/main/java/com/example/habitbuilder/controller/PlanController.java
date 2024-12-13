package com.example.habitbuilder.controller;

import com.example.habitbuilder.domain.vo.EventVo;
import com.example.habitbuilder.domain.vo.PlanOptionVo;
import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IEventService;
import com.example.habitbuilder.service.IPlanService;
import com.example.habitbuilder.service.IUserService;
import com.example.habitbuilder.serviceImpl.PlanServiceImpl;
import com.example.habitbuilder.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanServiceImpl planServiceImpl;
    @Autowired
    private IEventService iEventService;
    @Autowired
    private IPlanService planService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 每日计划选项
     *
     * @param token 令牌
     * @param date  日期
     * @return {@link Result }
     */
    @GetMapping("/dailyPlanOptions")
    public Result dailyPlanOptions(@RequestHeader("Authorization") String token, @RequestParam LocalDate date) {
        List<PlanOptionVo> planOptions = planService.dailyPlanOptions(token, date);
        return Result.success(planOptions, "查找成功");
    }

    @GetMapping("/completeEvent")
    public Result completeEvent(Integer eventId) {
        boolean flag = iEventService.completeEvent(eventId);
        if (!flag) {
            return Result.error("事件不存在");
        } else {
            int planId = iEventService.findPlanIdByEventId(eventId);
            int userId = planService.findUserIdByPlanId(planId);
            iUserService.changeScore(userId);
            return Result.success("完成成功");
        }
    }

    @GetMapping("/dailyEvent")
    public Result dailyEvent(@RequestHeader("Authorization") String token, @RequestParam LocalDate date) {
        List<EventVo> events = iEventService.dailyEvent(token, date);
        if (events.isEmpty()) {
            return Result.error("false");
        } else {
            for (int i = 0; i < events.size() - 1; i++) {
                EventVo currentEvent = events.get(i);
                EventVo nextEvent = events.get(i + 1);
                if (currentEvent.getEndTime().isAfter(nextEvent.getStartTime())) {
                    return Result.success(events, "true");
                }
            }
        }
        return Result.success(events, "false");
    }

    @PostMapping("/addPlan")
    public Result selectPlan(@RequestHeader("Authorization") String token, @RequestBody Plan plan) {
        plan.setCreateDate(LocalDateTime.now());
        return Result.success(planService.addPlan(token,plan), "计划添加成功");
    }

    @PostMapping("/autoAddPlan")
    public Result selectAutoPlan(@RequestHeader("Authorization")String token, @RequestBody Plan plan) {
        plan.setCreateDate(LocalDateTime.now().withNano(0));
        plan.setUserId(jwtUtil.extractUserId(token));
        HistoryConversation historyConversation = planServiceImpl.autoAddPlan(plan);
        return Result.success(historyConversation, "计划添加成功");
    }

    @PostMapping("deletePlan")
    public Result deletePlan(int planId) {
        planServiceImpl.deletePlan(planId);
        return Result.success("计划删除成功");
    }

    @PostMapping("/updatePlan")
    public Result updatePlan(@RequestBody Plan plan) {
        planServiceImpl.updatePlan(plan);
        return Result.success("计划更新成功");
    }

    @GetMapping("/myPlan")
    public Result getMyPlan(@RequestHeader("Authorization")String token) {
        int userId = jwtUtil.extractUserId(token);
        return Result.success(planServiceImpl.getMyPlan(userId), "获取我的所有计划成功"); //可以加判断返回
    }

    @GetMapping("/lowerScore")
    public Result lowerScore(int userId, LocalDate date) {
        int score = planServiceImpl.lowerScore(userId, date);
        if (score == -1) {
            return Result.error("今天的任务都完成啦");
        } else {
            return Result.success(score, "扣分成功");
        }
    }

    @GetMapping("/getPlanList")
    public Result getPlanList() {
        return Result.success(planServiceImpl.getPlanList(), "成功获取所有模板计划");
    }

    @PostMapping("/searchPlan")
    public Result searchPlan(String title, String tag, int userId) {
        List<Plan> plans = planServiceImpl.searchPlan(title, tag, userId);
        return Result.success(plans, "搜索成功");
    }

    @PostMapping("/fixPlan")
    public Result fixPlan(Integer planId, String request) {
        String[] planContent = planService.fixPlan(planId, request);
        if (planContent.length == 0) {
            return Result.error("该计划不存在");
        } else {
            return Result.success(planContent, "修改计划成功");
        }
    }

    @PostMapping("/completeFix")
    public Result completeFix(int planId, String[] planContent) {
        planService.completeFix(planId, planContent);
        return Result.success("修改成功");
    }

    @GetMapping("/searchByTag")
    public Result searchTagPlan(String tag, int userId) {
        return Result.success(planServiceImpl.searchByTag(tag, userId), "成功搜索主题模板");
    }
}
