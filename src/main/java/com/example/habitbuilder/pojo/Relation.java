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
 * @since 2024-07-03
 */
@Getter
@Setter
@TableName("relation")
@ApiModel(value = "Relation对象", description = "")
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relationId", type = IdType.AUTO)
    private Integer relationId;

    @TableField("id_1")
    private Integer id1;

    @TableField("id_2")
    private Integer id2;

    @TableField("ImageStr")
    private String imageStr;

    @TableField("isRead")
    private Boolean isRead;

    @TableField("relationType")
    private String relationType;
}
