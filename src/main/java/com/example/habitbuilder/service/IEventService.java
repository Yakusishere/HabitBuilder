package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Event;
import com.baomidou.mybatisplus.extension.service.IService;
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

   void createEvent(int planId,String eventDescription, String startTime, String endTime);

   boolean changeEvent(int eventId, String eventDescription, String startTime, String endTime);

   boolean deleteEvent(int eventId);

   boolean completeEvent(int eventId);

   List<Event> dailyPlanType(String date);

   List<Event> eventInPlan(String date, int planId);

   List<Event> dailyEvent(String date);

   List<Integer> findPlanIdByDate(String date);
}
