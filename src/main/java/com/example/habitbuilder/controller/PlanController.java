package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IEventService;
import com.example.habitbuilder.service.IPlanService;
import com.example.habitbuilder.service.IUserService;
import com.example.habitbuilder.serviceImpl.PlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
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
    private IPlanService iPlanService;
    @Autowired
    private IUserService iUserService;
    @PostMapping("/createEvent")
    public Result createEvent(@RequestBody Event eventRequest) {
        int planId = eventRequest.getPlanId();
        String eventDescription = eventRequest.getDescription();
        LocalTime startTime = eventRequest.getStartTime();
        LocalTime endTime = eventRequest.getEndTime();
        iEventService.createEvent(planId, eventDescription, startTime, endTime);
        return Result.success("创建事件成功");
    }
    @PutMapping("/changeEvent")
    public Result changeEvent(@RequestBody Event eventRequest){
        int eventId = eventRequest.getEventId();
        String eventDescription = eventRequest.getDescription();
        LocalTime startTime = eventRequest.getStartTime();
        LocalTime endTime = eventRequest.getEndTime();
        boolean flag=iEventService.changeEvent(eventId,eventDescription,startTime,endTime);
        if(!flag){
            return Result.error("事件不存在");
        }
        return Result.success("修改事件成功");
    }
    @GetMapping("/deleteEvent")
    public Result deleteEvent(@RequestParam Integer eventId){
        if(eventId==null){
            return Result.error("事件不存在");
        }
        boolean flag=iEventService.deleteEvent(eventId);
        if(!flag){
            return Result.error("事件不存在");
        }
        return Result.success("删除成功");
    }
    @PutMapping("/completeEvent")
    public Result completeEvent(@RequestParam int eventId){
        boolean flag=iEventService.completeEvent(eventId);
        if(!flag){
            return Result.error("事件不存在");
        }
        else{
            int planId=iEventService.findPlanIdByEventId(eventId);
            int userId=iPlanService.findUserIdByPlanId(planId);
            iUserService.changeScore(userId);
            return Result.success("完成成功");
        }
    }
    //获取当天用户要做的所有事件
    @GetMapping("/dailyPlanType")
    public Result dailyPlanType(int userId, LocalDate date){
        List<Object[]>plans =iPlanService.dailyPlanType(userId,date);
        if(plans.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(plans,"查找成功");
    }
    //获取当天对应计划的事件
    @PostMapping("/eventInPlan")
    public Result eventInPlan(@RequestBody Event eventRequest){
        int planId = eventRequest.getPlanId();
        LocalDate date=eventRequest.getExecutionDate();
        List<Event>events=iEventService.eventInPlan(date,planId);
        if(events.isEmpty()){
            return Result.error("计划中没有事件");
        }
        return Result.success(events,"查找成功");
    }
    @GetMapping("/dailyEvent")
    public Result dailyEvent(int userId,LocalDate date){
        List<Event>events =iEventService.dailyEvent(userId,date);
        if(events.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(events,"查找成功");
    }
    @PostMapping("/addPlan")
    public Result selectPlan(@RequestBody Plan plan) {
        plan.setCreateDate(LocalDateTime.now());
        planServiceImpl.addPlan(plan);
        return Result.success("计划添加成功");
    }
    @PostMapping("/autoAddPlan")
    public Result selectAutoPlan(@RequestBody Plan plan) {

        plan.setCreateDate(LocalDateTime.now().withNano(0));
        planServiceImpl.autoAddPlan(plan);
        return Result.success("计划添加成功");
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
    public Result getMyPlan(int userId) {
        return Result.success(planServiceImpl.getMyPlan(userId),"获取我的所有计划成功"); //可以加判断返回
    }

    @GetMapping("/lowerScore")
    public Result lowerScore(int userId,LocalDate date){
        int score=planServiceImpl.lowerScore(userId,date);
        if(score==-1){
            return Result.error("今天的任务都完成啦");
        }else {
            return Result.success(score,"扣分成功");
        }
    }

    @GetMapping("/searchPlan")
    public Result searchPlan(String title) {
        List<Plan>plans=planServiceImpl.searchPlan(title);
        if (plans.isEmpty()) {
            return Result.error("搜索失败");
        }
        return Result.success(plans,"搜索成功");
    }
}
