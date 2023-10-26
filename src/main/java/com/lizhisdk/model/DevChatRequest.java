package com.lizhisdk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 开发者对话请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevChatRequest implements Serializable {

    /**
     * 模型（助手）id
     */
    private Long modelId;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据库中的 accessKey, 系统分配的
     */
    String userAccessKey;

    /**
     * 数据库中的 secretKey, 系统分配的
     */
    String userSecretKey;

    private static final long serialVersionUID = 1L;

}