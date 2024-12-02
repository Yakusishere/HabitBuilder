package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2024-07-06
 */
@Getter
@Setter
@TableName("followuser")
@ApiModel(value = "Followuser对象", description = "")
public class Followuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "followUserId", type = IdType.AUTO)
    private Integer followUserId;

    @TableField("followerId")
    private Integer followerId;

    @TableField("followeeId")
    private Integer followeeId;
}
