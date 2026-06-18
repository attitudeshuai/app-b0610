package com.marathon.calculator.model;

import lombok.Getter;

/**
 * 距离类型枚举
 */
@Getter
public enum DistanceType {
    
    /**
     * 全程马拉松: 42.195公里
     */
    FULL(42.195, "全程马拉松"),
    
    /**
     * 半程马拉松: 21.0975公里
     */
    HALF(21.0975, "半程马拉松");
    
    private final double kilometers;
    private final String description;
    
    DistanceType(double kilometers, String description) {
        this.kilometers = kilometers;
        this.description = description;
    }
}
