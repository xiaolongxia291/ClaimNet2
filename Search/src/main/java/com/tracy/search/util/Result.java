package com.tracy.search.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;//0表示成功、1表示失败
    private String message;
    private List<T> data;
}
