package com.marathon.calculator.service;

import com.marathon.calculator.model.*;
import org.springframework.stereotype.Service;

/**
 * 配速计算服务
 */
@Service
public class PaceCalculatorService {
    
    /**
     * 根据目标完赛时间计算配速
     * 
     * @param request 时间请求
     * @return 计算结果
     */
    public CalculationResponse calculatePaceFromTime(TimeRequest request) {
        DistanceType distanceType = request.getDistanceType();
        double distance = request.getActualDistance(); // 使用实际距离（自定义或默认）
        int totalSeconds = request.getTotalSeconds();
        
        // 计算每公里配速（秒）
        double pacePerKm = (double) totalSeconds / distance;
        int paceMinutes = (int) (pacePerKm / 60);
        int paceSeconds = (int) (pacePerKm % 60);
        
        return CalculationResponse.builder()
                .distanceType(distanceType.getDescription())
                .distance(distance)
                .pace(formatTime(paceMinutes, paceSeconds))
                .paceMinutes(paceMinutes)
                .paceSeconds(paceSeconds)
                .finishTime(formatTime(request.getHours(), request.getMinutes(), request.getSeconds()))
                .finishHours(request.getHours())
                .finishMinutes(request.getMinutes())
                .finishSeconds(request.getSeconds())
                .totalSeconds(totalSeconds)
                .description(String.format("按照 %s/公里的配速完成%s，预计用时 %s",
                        formatTime(paceMinutes, paceSeconds),
                        distanceType.getDescription(),
                        formatTime(request.getHours(), request.getMinutes(), request.getSeconds())))
                .build();
    }
    
    /**
     * 根据目标配速计算完赛时间
     * 
     * @param request 配速请求
     * @return 计算结果
     */
    public CalculationResponse calculateTimeFromPace(PaceRequest request) {
        DistanceType distanceType = request.getDistanceType();
        double distance = request.getActualDistance(); // 使用实际距离（自定义或默认）
        int paceInSeconds = request.getPaceInSeconds();
        
        // 计算总完赛时间（秒）
        int totalSeconds = (int) (paceInSeconds * distance);
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        
        return CalculationResponse.builder()
                .distanceType(distanceType.getDescription())
                .distance(distance)
                .pace(formatTime(request.getPaceMinutes(), request.getPaceSeconds()))
                .paceMinutes(request.getPaceMinutes())
                .paceSeconds(request.getPaceSeconds())
                .finishTime(formatTime(hours, minutes, seconds))
                .finishHours(hours)
                .finishMinutes(minutes)
                .finishSeconds(seconds)
                .totalSeconds(totalSeconds)
                .description(String.format("按照 %s/公里的配速完成%s，预计用时 %s",
                        formatTime(request.getPaceMinutes(), request.getPaceSeconds()),
                        distanceType.getDescription(),
                        formatTime(hours, minutes, seconds)))
                .build();
    }
    
    /**
     * 格式化时间 (HH:MM:SS)
     */
    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    /**
     * 格式化时间 (MM:SS)
     */
    private String formatTime(int minutes, int seconds) {
        return String.format("%02d:%02d", minutes, seconds);
    }
}
