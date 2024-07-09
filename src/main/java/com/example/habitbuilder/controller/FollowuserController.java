package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Followuser;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.FollowuserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/followuser")
public class FollowuserController {
    @Autowired
    private FollowuserServiceImpl followUserService;

    @PostMapping("/addFollowUser")
    public Result addFollowUser(@RequestBody Followuser followuser) {

        followUserService.addFollowUser(followuser);
        return Result.success("关注成功");

    }

    @DeleteMapping("/deleteFollowUser")
    public Result deleteFollowUser(int followUserId) {
        followUserService.deleteFollowUser(followUserId);
        return Result.success("取消关注");
    }

    @GetMapping("/getFollowUsers")
    public Result getFollowUsers(int receiveUserId) {
        return Result.success(followUserService.getFollowUsers(receiveUserId),"获取关注列表成功");
    }

    @PostMapping("/getIfFollow") // 查看是否关注 sendUserId 是否关注 receiveUserId
    public Result getIfFollow(int sendUserId , int receiveUserId) {
        return Result.success(followUserService.getIfFollow(sendUserId,receiveUserId));
    }

}
