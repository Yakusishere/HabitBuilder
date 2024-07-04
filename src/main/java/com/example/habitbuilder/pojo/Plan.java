package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
 * @since 2024-07-03
 */
@Getter
@Setter
@TableName("plan")
@ApiModel(value = "Plan对象", description = "")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "planId", type = IdType.AUTO)
    private Integer planId;

    @TableField("userId")
    private Integer userId;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("completion_percentage")
    private Integer completionPercentage;

    @TableField("startDate")
    private LocalDate startDate;

    @TableField("endDate")
    private LocalDate endDate;

    @TableField("createDate")
    private LocalDateTime createDate;
}
