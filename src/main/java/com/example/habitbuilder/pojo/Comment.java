package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Getter
@Setter
@TableName("comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "commentId", type = IdType.AUTO)
    private Integer commentId;

    @TableField("commentCount")
    private Integer commentCount;

    @TableField("postId")
    private Integer postId;

    @TableField("content")
    private String content;

    @TableField("sendUserId")
    private Integer sendUserId;

    @TableField("receiveUserId")
    private Integer receiveUserId;

    @TableField("commentDate")
    private LocalDate commentDate;

    @TableField("likeCount")
    private Integer likeCount;

    @TableField("replyCount")
    private Integer replyCount;
}