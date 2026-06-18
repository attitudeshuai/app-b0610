package com.marathon.calculator.controller;

import com.marathon.calculator.model.CalculationResponse;
import com.marathon.calculator.model.PaceRequest;
import com.marathon.calculator.model.TimeRequest;
import com.marathon.calculator.service.PaceCalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 配速计算器控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/pace")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaceCalculatorController {
    
    private final PaceCalculatorService calculatorService;
    
    /**
     * 根据目标完赛时间计算配速
     * 
     * @param request 时间请求
     * @return 计算结果
     */
    @PostMapping("/calculate-from-time")
    public ResponseEntity<CalculationResponse> calculatePaceFromTime(
            @Valid @RequestBody TimeRequest request) {
        log.info("计算配速 - 输入时间: {}:{}:{}, 距离类型: {}", 
                request.getHours(), request.getMinutes(), request.getSeconds(), 
                request.getDistanceType());
        
        CalculationResponse response = calculatorService.calculatePaceFromTime(request);
        log.info("计算结果 - 配速: {}/公里", response.getPace());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 根据目标配速计算完赛时间
     * 
     * @param request 配速请求
     * @return 计算结果
     */
    @PostMapping("/calculate-from-pace")
    public ResponseEntity<CalculationResponse> calculateTimeFromPace(
            @Valid @RequestBody PaceRequest request) {
        log.info("计算完赛时间 - 配速: {}:{}/公里, 距离类型: {}", 
                request.getPaceMinutes(), request.getPaceSeconds(), 
                request.getDistanceType());
        
        CalculationResponse response = calculatorService.calculateTimeFromPace(request);
        log.info("计算结果 - 完赛时间: {}", response.getFinishTime());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("马拉松配速计算器服务运行正常");
    }
}
