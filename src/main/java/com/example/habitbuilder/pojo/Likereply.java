package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("likereply")
@ApiModel(value = "Likereply对象", description = "")
public class Likereply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "likeReplyId", type = IdType.AUTO)
    private Integer likeReplyId;

    @TableField("userId")
    private Integer userId;

    @TableField("replyId")
    private Integer replyId;

    @TableField("createTime")
    private LocalDateTime createTime;
}
