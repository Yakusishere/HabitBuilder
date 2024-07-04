package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IEventService;
import com.example.habitbuilder.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IEventService iEventService;
    @Autowired
    private IPlanService iPlanService;
    @PostMapping("/createEvent")
    public Result createEvent(int planId,String eventDescription,String startTime,String endTime){
        iEventService.createEvent(planId,eventDescription,startTime,endTime);
        return Result.success("创建事件成功");
    }
    @PutMapping("/changeEvent")
    public Result changeEvent(int eventId,String eventDescription,String startTime,String endTime){
        boolean flag=iEventService.changeEvent(eventId,eventDescription,startTime,endTime);
        if(!flag){
            return Result.error("事件不存在");
        }
        return Result.success("修改事件成功");
    }
    @GetMapping("/deleteEvent")
    public Result deleteEvent(int eventId){
        boolean flag=iEventService.deleteEvent(eventId);
        if(!flag){
            return Result.error("事件不存在");
        }
        return Result.success("删除成功");
    }
    @PutMapping("/completeEvent")
    public Result completeEvent(int eventId){
        boolean flag=iEventService.completeEvent(eventId);
        if(!flag){
            return Result.error("事件不存在");
        }
        return Result.success("完成成功");
    }
    @GetMapping("/dailyPlanType")
    public Result dailyPlanType(String date){
        List<String>plans =iPlanService.dailyPlanType(date);
        if(plans.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(plans,"查找成功");
    }
    @GetMapping("/eventInPlan")
    public Result eventInPlan(String date,int planId){
        List<Event>events=iEventService.eventInPlan(date,planId);
        if(events.isEmpty()){
            return Result.error("计划中没有事件");
        }
        return Result.success(events,"查找成功");
    }
    @GetMapping("/dailyEvent")
    public Result dailyEvent(String date){
        List<Event>events =iEventService.dailyEvent(date);
        if(events.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(events,"查找成功");
    }
}
