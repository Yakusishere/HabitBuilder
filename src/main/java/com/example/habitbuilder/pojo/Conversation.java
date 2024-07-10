package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2024-07-06
 */
@Getter
@Setter
@TableName("conversation")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Conversation对象", description = "")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "conversationId", type = IdType.AUTO)
    private Integer conversationId;

    @TableField("userId")
    private Integer userId;

    @TableField("question")
    private String question;

    @TableField("answer")
    private String answer;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("historyConversationId")
    private Integer historyConversationId;

}
