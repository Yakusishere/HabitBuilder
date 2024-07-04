package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IPlanService iPlanService;
    @PostMapping("/createEvent")
    public Result createEvent(String eventDescription,String startTime,String endTime){
        return Result.success("创建事件成功");
    }
}
