package com.example.habitbuilder.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("post")
@ApiModel(value = "Post对象", description = "")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "postId", type = IdType.AUTO)
    private Integer postId;

    @TableField("userId")
    private Integer userId;

    @TableField("isViewable")
    private Boolean isViewable;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("publishDate")
    private LocalDate publishDate;

    @TableField("LikeCount")
    private Integer likeCount;

    @TableField("FavCount")
    private Integer favCount;

    @TableField("CommentCount")
    private Integer commentCount;
}
