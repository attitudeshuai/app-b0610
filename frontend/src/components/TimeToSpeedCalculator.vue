<template>
  <div>
    <h2 class="text-2xl font-bold text-gray-800 mb-6">
      根据目标时间计算配速
    </h2>
    
    <form @submit.prevent="handleSubmit" class="space-y-6">
      <!-- 距离类型选择 -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-3">
          选择距离
        </label>
        <div class="grid grid-cols-2 gap-4">
          <button
            type="button"
            @click="form.distanceType = 'FULL'"
            :class="[
              'py-4 px-6 rounded-lg font-semibold transition-all duration-300',
              form.distanceType === 'FULL'
                ? 'bg-gradient-to-r from-blue-500 to-cyan-500 text-white shadow-md'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            ]"
          >
            🏃 全程马拉松
            <div class="text-xs opacity-80 mt-1">
              {{ customSettings?.fullDistance || 42.195 }} 公里
            </div>
          </button>
          <button
            type="button"
            @click="form.distanceType = 'HALF'"
            :class="[
              'py-4 px-6 rounded-lg font-semibold transition-all duration-300',
              form.distanceType === 'HALF'
                ? 'bg-gradient-to-r from-blue-500 to-cyan-500 text-white shadow-md'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            ]"
          >
            🏃‍♀️ 半程马拉松
            <div class="text-xs opacity-80 mt-1">
              {{ customSettings?.halfDistance || 21.0975 }} 公里
            </div>
          </button>
        </div>
      </div>

      <!-- 时间输入 -->
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-3">
          目标完赛时间
        </label>
        <div class="grid grid-cols-3 gap-4">
          <div>
            <label class="block text-xs text-gray-500 mb-2">小时</label>
            <input
              v-model.number="form.hours"
              type="number"
              min="0"
              max="23"
              class="input-field text-center text-lg font-semibold"
              placeholder="0"
              required
            />
          </div>
          <div>
            <label class="block text-xs text-gray-500 mb-2">分钟</label>
            <input
              v-model.number="form.minutes"
              type="number"
              min="0"
              max="59"
              class="input-field text-center text-lg font-semibold"
              placeholder="0"
              required
            />
          </div>
          <div>
            <label class="block text-xs text-gray-500 mb-2">秒</label>
            <input
              v-model.number="form.seconds"
              type="number"
              min="0"
              max="59"
              class="input-field text-center text-lg font-semibold"
              placeholder="0"
              required
            />
          </div>
        </div>
        <p class="text-sm text-gray-500 mt-2">
          当前输入：{{ formatTime(form.hours, form.minutes, form.seconds) }}
        </p>
      </div>

      <!-- 提交按钮 -->
      <button
        type="submit"
        :disabled="loading"
        class="btn-primary w-full text-lg"
      >
        <span v-if="loading">计算中...</span>
        <span v-else>🚀 开始计算</span>
      </button>
    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const props = defineProps({
  loading: Boolean,
  customSettings: Object
})

const emit = defineEmits(['calculate'])

const form = reactive({
  distanceType: 'FULL',
  hours: 3,
  minutes: 30,
  seconds: 0
})

const formatTime = (h, m, s) => {
  return `${String(h || 0).padStart(2, '0')}:${String(m || 0).padStart(2, '0')}:${String(s || 0).padStart(2, '0')}`
}

const handleSubmit = () => {
  emit('calculate', {
    distanceType: form.distanceType,
    hours: form.hours || 0,
    minutes: form.minutes || 0,
    seconds: form.seconds || 0
  })
}
</script>
