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
 * @since 2024-08-15
 */
@Getter
@Setter
@TableName("reply")
@ApiModel(value = "Reply对象", description = "")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "replyId", type = IdType.AUTO)
    private Integer replyId;

    @TableField("commentId")
    private Integer commentId;

    @TableField("replyToId")
    private Integer replyToId;

    @TableField("userId")
    private Integer userId;

    @TableField("content")
    private String content;

    @TableField("commentDate")
    private LocalDate commentDate;

    @TableField("likeCount")
    private Integer likeCount;
}
