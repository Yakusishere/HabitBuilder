package com.example.habitbuilder.controller;

import com.example.habitbuilder.utils.JwtUtil;
import com.example.habitbuilder.utils.LoginManager;
import com.example.habitbuilder.annotation.ManagerLog;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.ManagerServiceImpl;
import com.example.habitbuilder.serviceImpl.PlanServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.habitbuilder.pojo.Manager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerServiceImpl managerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/managerLogin")
    public Result managerLogin(@RequestBody Manager manager) throws Exception {
        String managerName = manager.getManagerName();
        String password = manager.getPassword();
        LoginManager manager1 = (LoginManager) managerService.loadUserByUsername(managerName);
        if(manager1 != null){
            if(passwordEncoder.matches(password, manager1.getPassword())){
                //生成jwt token
                String token = jwtUtil.createJwt(manager1);
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("token", token);
                responseData.put("manager", manager1);
                return Result.success(responseData,"管理员登录成功");
            }
            else{
                return Result.error("密码错误");
            }
        }else{
            return Result.error("账户不存在");
        }
    }

    @PostMapping("/managerRegister")
    public Result managerRegister(@RequestBody Manager manager){
        String managerName = manager.getManagerName();
        String password = manager.getPassword();
        Manager manager1 =  managerService.getManagerByManagerName(managerName); //查找是否存在
        if(manager1 != null){
            return Result.error("账户已存在");
        }else{
            String encodedPassword = passwordEncoder.encode(password);
            managerService.managerRegister(new Manager(0,managerName,encodedPassword));
            return Result.success("管理员注册成功");
        }
    }

    @GetMapping("/getManagerList")
    public Result getManagerList(){
        return Result.success(managerService.getManagerList(),"获取管理员列表成功");
    }

    @GetMapping("/getUserList")
    public Result getUserList(){
        return Result.success(managerService.getUserList(),"获取用户列表成功");
    }

    @GetMapping("/searchUser")
    public Result searchUser(@RequestParam  String username){
        System.out.println("搜索的用户为"+username);
        return Result.success(managerService.searchUser(username),"搜索用户成功");
    }

    @GetMapping("/searchPlan")
    public Result managerSearchPlan(String title) {
        return Result.success(managerService.managerSearchPlan(title),"成功搜索模板计划");
    }

    @GetMapping("/getPlanList")
    public Result getPlanList(){
        return Result.success(managerService.getPlanList(),"管理员获取计划模板");
    }

    @PostMapping("/addPlan")
    @ManagerLog(opreation = "上新计划")
    public Result addPlan(@RequestBody Plan plan){
        plan.setCreateDate(LocalDateTime.now());
        return Result.success( managerService.addPlan(plan),"上新计划成功");
    }

    @DeleteMapping ("/deletePlan")
    @ManagerLog(opreation = "下架计划")
    public Result deletePlan(int planId){
        managerService.deletePlan(planId);
        return Result.success("下架计划成功");
    }

    @PostMapping("/updatePlan")
    @ManagerLog(opreation = "更新计划")
    public Result updatePlan(@RequestBody Plan plan){
        managerService.updatePlan(plan);
        return Result.success("更新计划成功");
    }

    @GetMapping("/getUserStatistics")
    public Result getUserStatistics() {
        int totalUserCount = managerService.getUserCount();
        int activeUserCount = managerService.getActiveUserCount();
        int usersWithPostsOnly = managerService.getUsersWithPostsOnly();
        int usersWithPlansOnly = managerService.getUsersWithPlansOnly();
        int usersWithBothPostsAndPlans = managerService.getUsersWithBothPostsAndPlans();

        int[] userStatistics = new int[]{
                totalUserCount,
                activeUserCount,
                usersWithPostsOnly,
                usersWithPlansOnly,
                usersWithBothPostsAndPlans
        };

        return Result.success(userStatistics, "获取用户统计数据成功");
    }




    @GetMapping("/getPlanCountByTag")
    public Result getPlanCountByTag() {
        int[] planCountByTag = managerService.getPlanCountByTag();
        return Result.success(planCountByTag, "获取每个tag的计划数量成功");
    }



    @GetMapping("/getGeneralStatistics")
    public Result getGeneralStatistics() {
        int totalPlanCount = managerService.getTotalPlanCount();
        int totalPostCount = managerService.getTotalPostCount();
        int totalConversationCount = managerService.getTotalConversationCount();

        int[] generalStatistics = new int[]{
                totalPlanCount,
                totalPostCount,
                totalConversationCount
        };

        return Result.success(generalStatistics, "获取总统计数据成功");
    }


    @GetMapping("/getAverageStatistics")
    public Result getAverageStatistics() {
        double averagePlanCount = managerService.getAveragePlanCount();
        double averagePostCount = managerService.getAveragePostCount();
        double averageConversationCount = managerService.getAverageConversationCount();
        double averageEventCount = managerService.getAverageEventCount();

        int[] averageStatistics = new int[]{
                (int) Math.ceil(averagePlanCount),
                (int) Math.ceil(averagePostCount),
                (int) Math.ceil(averageConversationCount),
                (int) Math.ceil(averageEventCount)
        };

        return Result.success(averageStatistics, "获取平均统计数据成功");
    }
}
