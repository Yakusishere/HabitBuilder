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
 * @since 2024-07-06
 */
@Getter
@Setter
@TableName("conversation")
@ApiModel(value = "Conversation对象", description = "")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "conversationId", type = IdType.AUTO)
    private Integer conversationId;

    @TableField("userId")
    private Integer userId;

    @TableField("title")
    private String title;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("historyConversationId")
    private Integer historyConversationId;

    @TableField("isAnswer")
    private boolean isAnswer;
}
