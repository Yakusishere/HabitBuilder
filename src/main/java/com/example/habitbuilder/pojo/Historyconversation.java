package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
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
@TableName("historyconversation")
@ApiModel(value = "Historyconversation对象", description = "")
public class HistoryConversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "historyConversationId", type = IdType.AUTO)
    private Integer historyConversationId;

    @TableField("title")
    private String title;

   @TableField("userId")
    private Integer userId;

   @TableField("createTime")
    private LocalDateTime createTime;
}
