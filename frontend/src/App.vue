<template>
  <div class="min-h-screen py-8 px-4 sm:px-6 lg:px-8">
    <div class="max-w-6xl mx-auto">
      <!-- 标题 -->
      <header class="text-center mb-12 animate-fade-in relative">
        <!-- 设置按钮 -->
        <button
          @click="showSettings = true"
          class="absolute top-0 right-0 p-3 text-gray-500 hover:text-blue-600 transition-colors rounded-lg hover:bg-gray-100"
          title="距离设置"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
        </button>
        
        <div class="inline-flex items-center justify-center w-20 h-20 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-full mb-4 shadow-lg">
          <span class="text-4xl">🏃</span>
        </div>
        <h1 class="text-4xl md:text-5xl font-bold text-gray-800 mb-3">
          马拉松配速计算器
        </h1>
        <p class="text-gray-600 text-lg">
          精确计算您的配速和完赛时间
        </p>
      </header>

      <!-- Tab切换 -->
      <div class="flex justify-center mb-8 gap-4">
        <button
          @click="activeTab = 'time'"
          :class="[
            'tab-button',
            activeTab === 'time' ? 'tab-button-active' : 'tab-button-inactive'
          ]"
        >
          📊 时间 → 配速
        </button>
        <button
          @click="activeTab = 'pace'"
          :class="[
            'tab-button',
            activeTab === 'pace' ? 'tab-button-active' : 'tab-button-inactive'
          ]"
        >
          ⏱️ 配速 → 时间
        </button>
      </div>

      <!-- 内容区域 -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- 左侧：输入区 -->
        <div class="card animate-fade-in">
          <TimeToSpeedCalculator 
            v-if="activeTab === 'time'" 
            @calculate="handleTimeCalculation"
            :loading="loading"
            :custom-settings="distanceSettings"
          />
          <PaceToTimeCalculator 
            v-else 
            @calculate="handlePaceCalculation"
            :loading="loading"
            :custom-settings="distanceSettings"
          />
        </div>

        <!-- 右侧：结果展示 -->
        <div class="card animate-fade-in">
          <ResultDisplay :result="result" :loading="loading" :error="error" />
        </div>
      </div>

      <!-- 页脚说明 -->
      <footer class="mt-12 text-center text-gray-500 text-sm">
        <p v-if="distanceSettings.fullDistance || distanceSettings.halfDistance" class="mb-2 text-blue-600 font-semibold">
          ⚙️ 使用自定义距离：
          <span v-if="distanceSettings.fullDistance">全程 {{ distanceSettings.fullDistance }}km</span>
          <span v-if="distanceSettings.fullDistance && distanceSettings.halfDistance"> | </span>
          <span v-if="distanceSettings.halfDistance">半程 {{ distanceSettings.halfDistance }}km</span>
        </p>
        <p>💡 提示：全程马拉松为 42.195 公里，半程马拉松为 21.0975 公里</p>
      </footer>

      <!-- 距离设置弹窗 -->
      <DistanceSettings
        :is-open="showSettings"
        :settings="distanceSettings"
        @close="showSettings = false"
        @save="saveDistanceSettings"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import TimeToSpeedCalculator from './components/TimeToSpeedCalculator.vue'
import PaceToTimeCalculator from './components/PaceToTimeCalculator.vue'
import ResultDisplay from './components/ResultDisplay.vue'
import DistanceSettings from './components/DistanceSettings.vue'
import { calculatePaceFromTime, calculateTimeFromPace } from './api/calculator'

const activeTab = ref('time')
const result = ref(null)
const loading = ref(false)
const error = ref(null)
const showSettings = ref(false)
const distanceSettings = ref({
  fullDistance: null,
  halfDistance: null
})

// 从 localStorage 加载距离设置
onMounted(() => {
  const saved = localStorage.getItem('marathonDistanceSettings')
  if (saved) {
    try {
      distanceSettings.value = JSON.parse(saved)
    } catch (e) {
      console.error('Failed to load distance settings:', e)
    }
  }
})

// 保存距离设置
const saveDistanceSettings = (settings) => {
  distanceSettings.value = settings
  localStorage.setItem('marathonDistanceSettings', JSON.stringify(settings))
  showSettings.value = false
}

// 获取当前距离（自定义或默认）
const getCustomDistance = (distanceType) => {
  if (distanceType === 'FULL' && distanceSettings.value.fullDistance) {
    return distanceSettings.value.fullDistance
  }
  if (distanceType === 'HALF' && distanceSettings.value.halfDistance) {
    return distanceSettings.value.halfDistance
  }
  return null
}

const handleTimeCalculation = async (data) => {
  loading.value = true
  error.value = null
  result.value = null
  
  try {
    // 添加自定义距离到请求
    const requestData = {
      ...data,
      customDistance: getCustomDistance(data.distanceType)
    }
    const response = await calculatePaceFromTime(requestData)
    result.value = response
  } catch (err) {
    error.value = err.response?.data?.message || '计算失败，请检查输入并重试'
  } finally {
    loading.value = false
  }
}

const handlePaceCalculation = async (data) => {
  loading.value = true
  error.value = null
  result.value = null
  
  try {
    // 添加自定义距离到请求
    const requestData = {
      ...data,
      customDistance: getCustomDistance(data.distanceType)
    }
    const response = await calculateTimeFromPace(requestData)
    result.value = response
  } catch (err) {
    error.value = err.response?.data?.message || '计算失败，请检查输入并重试'
  } finally {
    loading.value = false
  }
}
</script>
