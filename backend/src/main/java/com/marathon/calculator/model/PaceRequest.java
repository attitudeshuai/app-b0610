package com.marathon.calculator.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 根据配速计算时间的请求模型
 */
@Data
public class PaceRequest {
    
    /**
     * 配速-分钟 (2-20)
     */
    @NotNull(message = "配速分钟不能为空")
    @Min(value = 2, message = "配速分钟必须大于等于2")
    @Max(value = 20, message = "配速分钟必须小于等于20")
    private Integer paceMinutes;
    
    /**
     * 配速-秒 (0-59)
     */
    @NotNull(message = "配速秒不能为空")
    @Min(value = 0, message = "配速秒必须大于等于0")
    @Max(value = 59, message = "配速秒必须小于等于59")
    private Integer paceSeconds;
    
    /**
     * 距离类型: FULL(全程马拉松), HALF(半程马拉松)
     */
    @NotNull(message = "距离类型不能为空")
    private DistanceType distanceType;
    
    /**
     * 自定义距离（公里），可选
     * 如果提供，将使用此距离而不是 distanceType 的默认值
     */
    @Min(value = 1, message = "自定义距离必须大于1公里")
    @Max(value = 200, message = "自定义距离必须小于200公里")
    private Double customDistance;
    
    /**
     * 获取实际使用的距离
     */
    public double getActualDistance() {
        return customDistance != null ? customDistance : distanceType.getKilometers();
    }
    
    /**
     * 获取每公里配速的总秒数
     */
    public int getPaceInSeconds() {
        return paceMinutes * 60 + paceSeconds;
    }
}
