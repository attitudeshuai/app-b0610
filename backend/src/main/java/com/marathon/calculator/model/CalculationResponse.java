package com.marathon.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计算结果响应模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponse {
    
    /**
     * 距离类型描述
     */
    private String distanceType;
    
    /**
     * 距离（公里）
     */
    private Double distance;
    
    /**
     * 配速（分:秒/公里）
     */
    private String pace;
    
    /**
     * 配速-分钟
     */
    private Integer paceMinutes;
    
    /**
     * 配速-秒
     */
    private Integer paceSeconds;
    
    /**
     * 完赛时间（时:分:秒）
     */
    private String finishTime;
    
    /**
     * 完赛时间-小时
     */
    private Integer finishHours;
    
    /**
     * 完赛时间-分钟
     */
    private Integer finishMinutes;
    
    /**
     * 完赛时间-秒
     */
    private Integer finishSeconds;
    
    /**
     * 总完赛秒数
     */
    private Integer totalSeconds;
    
    /**
     * 计算说明
     */
    private String description;
}
