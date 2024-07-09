package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Collectpost;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.CollectpostServiceImpl;
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
@RequestMapping("/collectpost")
public class CollectpostController {
    @Autowired
    CollectpostServiceImpl collectpostService;

    @PostMapping("/addCollection")
    public Result addCollection( @RequestBody Collectpost collectpost) {
        collectpostService.addCollection(collectpost);
        return Result.success("收藏成功");
    }

    @DeleteMapping("/deleteCollection")
    public Result deleteCollection(int collectionId) {
        collectpostService.deleteCollection(collectionId);
        return Result.success("取消收藏成功");
    }
    // 是否收藏
    @PostMapping("/getPostCollection")
    public Result getPostCollection( int userId,int postId) {

        return Result.success(collectpostService.getPostCollections(userId,postId));

    }

    @GetMapping("/getCollections")
    public Result getCollections(int userId) {
        return Result.success(collectpostService.getCollections(userId),"收藏列表获取成功");
    }
}
