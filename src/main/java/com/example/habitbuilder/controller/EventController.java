package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventServiceImpl eventService;

    @GetMapping("/getPlanEvents")
    public Result getPlanEvents(int planId) {
        return Result.success(eventService.getPlanEvents(planId),"成功获取该计划的所有活动");
    }

    @PostMapping("/addEvent")
    public Result addEvent(@RequestBody Event event) {
        eventService.addEvent(event);
        return Result.success("成功新增活动");
    }
}
