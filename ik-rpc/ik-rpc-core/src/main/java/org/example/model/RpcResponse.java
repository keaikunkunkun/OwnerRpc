package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    //响应结果
    private Object result;

    //响应结果类型
    private Class<?> type;

    //响应信息
    private String message;

    //异常信息
    private Exception exception;
}
