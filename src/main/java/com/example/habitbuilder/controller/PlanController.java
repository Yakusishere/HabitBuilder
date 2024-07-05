package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IEventService;
import com.example.habitbuilder.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private IEventService iEventService;
    @Autowired
    private IPlanService iPlanService;
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
        return Result.success("完成成功");
    }
    @GetMapping("/dailyPlanType")
    public Result dailyPlanType(@RequestParam LocalDate date){
        List<String>plans =iPlanService.dailyPlanType(date);
        if(plans.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(plans,"查找成功");
    }
    @GetMapping("/eventInPlan")
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
    public Result dailyEvent(@RequestParam LocalDate date){
        List<Event>events =iEventService.dailyEvent(date);
        if(events.isEmpty()){
            return Result.error("今天没有计划");
        }
        return Result.success(events,"查找成功");
    }
}
