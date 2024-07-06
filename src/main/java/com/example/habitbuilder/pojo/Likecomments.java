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
@TableName("likecomments")
@ApiModel(value = "Likecomments对象", description = "")
public class Likecomments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "LikeCommentId", type = IdType.AUTO)
    private Integer likeCommentId;

    @TableField("userId")
    private Integer userId;

    @TableField("commentId")
    private Integer commentId;

    @TableField("ImageStr")
    private String imageStr;

    @TableField("isRead")
    private Boolean isRead;
}
