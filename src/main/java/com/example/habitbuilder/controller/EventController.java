package com.example.habitbuilder.controller;

import com.example.habitbuilder.domain.bo.EventBo;
import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IEventService;
import com.example.habitbuilder.serviceImpl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	IEventService eventService;

	@PostMapping("/list")
	public Result getEventList(@RequestHeader("Authorization") String token, @RequestBody EventBo bo) {
		return Result.success(eventService.getEventList(token, bo), "成功获取时间列表");
	}

	@PostMapping("/add")
	public Result addEvent(@RequestHeader("Authorization") String token, @RequestBody Event event) {
		eventService.addEvent(token, event);
		return Result.success("成功新增活动");
	}

	@PutMapping("/edit")
	public Result editEvent(@RequestHeader("Authorization") String token, @RequestBody Event event) {
		eventService.changeEvent(token, event);
		return Result.success("成功更改活动");
	}

	@DeleteMapping("/delete/{eventId}")
	public Result deleteEvent(@RequestHeader("Authorization")String token,@PathVariable int eventId){
		eventService.deleteEvent(token,eventId);
		return Result.success("成功删除");
	}

	@PostMapping("/setColor")
	public Result setColor(int userId, LocalDate localDate) {
		return Result.success(eventService.setColor(userId, localDate), "成功");
	}
}
