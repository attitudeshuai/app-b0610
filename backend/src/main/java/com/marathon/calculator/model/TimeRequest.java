package com.marathon.calculator.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 根据时间计算配速的请求模型
 */
@Data
public class TimeRequest {
    
    /**
     * 小时 (0-23)
     */
    @Min(value = 0, message = "小时必须大于等于0")
    @Max(value = 23, message = "小时必须小于等于23")
    private Integer hours;
    
    /**
     * 分钟 (0-59)
     */
    @Min(value = 0, message = "分钟必须大于等于0")
    @Max(value = 59, message = "分钟必须小于等于59")
    private Integer minutes;
    
    /**
     * 秒 (0-59)
     */
    @Min(value = 0, message = "秒必须大于等于0")
    @Max(value = 59, message = "秒必须小于等于59")
    private Integer seconds;
    
    /**
     * 距离类型: FULL(全程马拉松), HALF(半程马拉松)
     */
    @NotNull(message = "距离类型不能为空")
    private DistanceType distanceType;
    
    /**
     * 自定义距离（公里），可选
     * 如果提供，将使用此距离而不是 distanceType 的默认值
     */
    @Min(value = 1, message = "自定义距离必须大于等于1公里")
    @Max(value = 200, message = "自定义距离必须小于等于200公里")
    private Double customDistance;
    
    /**
     * 获取实际使用的距离
     */
    public double getActualDistance() {
        return customDistance != null ? customDistance : distanceType.getKilometers();
    }
    
    /**
     * 获取小时（空值默认为0）
     */
    public int getHours() {
        return hours != null ? hours : 0;
    }
    
    /**
     * 获取分钟（空值默认为0）
     */
    public int getMinutes() {
        return minutes != null ? minutes : 0;
    }
    
    /**
     * 获取秒（空值默认为0）
     */
    public int getSeconds() {
        return seconds != null ? seconds : 0;
    }
    
    /**
     * 获取总秒数
     */
    public int getTotalSeconds() {
        return getHours() * 3600 + getMinutes() * 60 + getSeconds();
    }
}
