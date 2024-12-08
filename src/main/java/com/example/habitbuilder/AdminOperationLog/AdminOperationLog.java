package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.*;

/**
 * 
 * @TableName admin_operation_log
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="admin_operation_log")
public class AdminOperationLog implements Serializable {
    /**
     * 日志ID
     */
    @TableId(value = "logId", type = IdType.AUTO)
    private Integer logId;

    /**
     * 管理员ID
     */
    @TableField(value = "managerId")
    private Integer managerId;

    /**
     * 操作描述
     */
    @TableField(value = "operation")
    private String operation;

    /**
     * 操作时间
     */
    @TableField(value = "operationTime")
    private Date operationTime;

    private static final long serialVersionUID = 1L;
}