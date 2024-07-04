package com.example.habitbuilder.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//0-成功 1-失败
    private String message;//
    private T data;

    //操作成功带数据
    public static <E> Result<E> success(E data,String message){
        return new Result<>(0,"操作成功",data);
    }

    //操作成功不带数据
    public static Result success(String message){
        return new Result(0,"操作成功",null);
    }

    //操作失败
    public  static Result error(String message){
        return new Result(1,message,null);
    }
}
