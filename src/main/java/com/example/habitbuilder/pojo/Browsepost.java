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
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("browsepost")
@ApiModel(value = "Browsepost对象", description = "")
public class Browsepost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "browse_post_id", type = IdType.AUTO)
    private Integer browsePostId;

    @TableField("userId")
    private Integer userId;

    @TableField("postId")
    private Integer postId;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty("单位：秒")
    @TableField("browse_time")
    private Integer browseTime;
}
