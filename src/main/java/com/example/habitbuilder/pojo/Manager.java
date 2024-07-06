package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@TableName("manager")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Manager对象", description = "")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "managerId", type = IdType.AUTO)
    private Integer managerId;

    @TableField("managerName")
    private String managerName;

    @TableField("password")
    private String password;
}
