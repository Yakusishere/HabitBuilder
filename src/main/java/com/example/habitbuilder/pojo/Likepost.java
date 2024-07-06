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
@TableName("likepost")
@ApiModel(value = "Likepost对象", description = "")
public class Likepost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "LikePostId", type = IdType.AUTO)
    private Integer likePostId;

    @TableField("userId")
    private Integer userId;

    @TableField("postId")
    private Integer postId;

    @TableField("ImageStr")
    private String imageStr;

    @TableField("isRead")
    private Boolean isRead;
}
