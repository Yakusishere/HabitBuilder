package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Event;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IEventService extends IService<Event> {

   void createEvent(int planId, String eventDescription, LocalTime startTime, LocalTime endTime);

   boolean changeEvent(int eventId, String eventDescription, LocalTime startTime, LocalTime endTime);

   boolean deleteEvent(Integer eventId);

   boolean completeEvent(int eventId);

   List<Event> eventInPlan(LocalDate date, int planId);

   List<Event> dailyEvent(LocalDate date);

   List<Integer> findPlanIdByDate(LocalDate date);
}
