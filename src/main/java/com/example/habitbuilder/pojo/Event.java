package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
@TableName("event")
@ApiModel(value = "Event对象", description = "")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "eventId", type = IdType.AUTO)
    private Integer eventId;

    @TableField("planId")
    private Integer planId;

    @TableField("description")
    private String description;

    @TableField("executionDate")
    private LocalDate executionDate;

    @TableField("startTime")
    private LocalTime startTime;

    @TableField("endTime")
    private LocalTime endTime;

    @TableField("isCompleted")
    private Boolean isCompleted;
}
