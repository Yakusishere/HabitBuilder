package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.mapper.EventMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private PlanMapper planMapper;
    @Override
    public void createEvent(int planId,String eventDescription, LocalTime startTime, LocalTime endTime) {
        LocalDate today = LocalDate.now();
        eventMapper.insert(new Event(null,planId,eventDescription,today,startTime,endTime,false));
    }

    @Override
    public boolean changeEvent(int eventId, String eventDescription, LocalTime startTime, LocalTime endTime) {
        Event event = getById(eventId);
        if (event != null) {
            event.setDescription(eventDescription);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            updateById(event);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteEvent(Integer eventId) {
        Event event = getById(eventId);
        if (event != null) {
            removeById(eventId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean completeEvent(int eventId) {
        Event event = getById(eventId);
        if (event != null) {
            event.setIsCompleted(true);
            updateById(event);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Event> eventInPlan(LocalDate date, int planId) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(Map.of( "planId", planId, "executionDate",date));
        List<Event>events=eventMapper.selectList(queryWrapper);
        events.sort(Comparator.comparing(Event::getStartTime));
        return events;
    }

    @Override
    public List<Event> dailyEvent(int userId,LocalDate date) {
        QueryWrapper<Plan> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userId",userId);
        List<Plan> plans=planMapper.selectList(queryWrapper1);
        List<Integer> planIds=plans.stream()
                .map(Plan::getPlanId)
                .distinct()
                .collect(Collectors.toList());
        if(plans.isEmpty()){
            return new ArrayList<>();
        }
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("planId",planIds).eq("executionDate",date);
        List<Event> events=eventMapper.selectList(queryWrapper);
        events.sort(Comparator.comparing(Event::getStartTime));
        return events;
    }

    @Override
    public List<Integer> findPlanIdByDate(LocalDate date) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("executionDate", date);
        List<Event> events = eventMapper.selectList(queryWrapper);
        return events.stream()
                .map(Event::getPlanId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public int findPlanIdByEventId(int eventId) {
        Event event=getById(eventId);
        return event.getPlanId();
    }

    @Override
    public List<Event> getPlanEvents(int planId) {
        return eventMapper.selectList(new QueryWrapper<Event>().eq("planId", planId));
    }

    @Override
    public void addEvent(Event event){
        eventMapper.insert(event);
    }


}
