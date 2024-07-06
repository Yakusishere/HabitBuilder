package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.habitbuilder.pojo.Manager;
import java.time.LocalDateTime;

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

    @PostMapping("/managerLogin")
    public Result managerLogin(@RequestBody Manager manager){
        String managerName = manager.getManagerName();
        String password = manager.getPassword();
        Manager manager1 =  managerService.getManagerByManagerName(managerName);
        if(manager1 != null){
            if(password.equals(manager1.getPassword())){
                return Result.success("管理员登录成功");
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
            managerService.managerRegister(new Manager(0,managerName,password));
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

    @PostMapping("/addPlan")
    public Result addPlan(@RequestBody Plan plan){
        plan.setCreateDate(LocalDateTime.now());
        managerService.addPlan(plan);
        return Result.success("上新计划成功");
    }

    @DeleteMapping ("/deletePlan")
    public Result deletePlan(int planId){
        managerService.deletePlan(planId);
        return Result.success("下架计划成功");
    }

    @PostMapping("/updatePlan")
    public Result updatePlan(@RequestBody Plan plan){
        managerService.updatePlan(plan);
        return Result.success("更新计划成功");
    }

}
