package com.example.habitbuilder.domain.vo;

import lombok.Data;

@Data
public class UserDetailVo {
    private String userName;

    private String nickName;

    private String avatarImg;

    private Integer myScore;

    private String followCount;

    private String fanCount;
}
