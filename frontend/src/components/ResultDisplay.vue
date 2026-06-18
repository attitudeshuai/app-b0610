<template>
  <div>
    <h2 class="text-2xl font-bold text-gray-800 mb-6">
      计算结果
    </h2>

    <!-- 加载中 -->
    <div v-if="loading" class="flex flex-col items-center justify-center py-12">
      <div class="animate-spin rounded-full h-16 w-16 border-4 border-blue-500 border-t-transparent"></div>
      <p class="text-gray-600 mt-4">计算中...</p>
    </div>

    <!-- 错误提示 -->
    <div v-else-if="error" class="bg-red-50 border-2 border-red-200 rounded-lg p-6 text-center">
      <div class="text-4xl mb-3">❌</div>
      <p class="text-red-600 font-semibold">{{ error }}</p>
    </div>

    <!-- 空状态 -->
    <div v-else-if="!result" class="flex flex-col items-center justify-center py-12 text-gray-400">
      <div class="text-6xl mb-4">📊</div>
      <p class="text-lg">请输入数据并点击计算</p>
    </div>

    <!-- 结果展示 -->
    <div v-else class="space-y-6 animate-fade-in">
      <!-- 距离信息 -->
      <div class="bg-gradient-to-r from-blue-50 to-cyan-50 rounded-lg p-5">
        <div class="text-sm text-gray-600 mb-1">比赛类型</div>
        <div class="text-2xl font-bold text-gray-800">
          {{ result.distanceType }}
        </div>
        <div class="text-sm text-gray-500 mt-1">
          {{ result.distance }} 公里
        </div>
      </div>

      <!-- 配速信息 -->
      <div class="bg-white border-2 border-blue-200 rounded-lg p-6">
        <div class="flex items-center justify-between mb-2">
          <span class="text-sm font-semibold text-gray-600">配速</span>
          <span class="text-xs bg-blue-100 text-blue-700 px-3 py-1 rounded-full">
            每公里
          </span>
        </div>
        <div class="text-5xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-cyan-600">
          {{ result.pace }}
        </div>
        <div class="text-sm text-gray-500 mt-2">
          {{ result.paceMinutes }} 分 {{ result.paceSeconds }} 秒 / 公里
        </div>
      </div>

      <!-- 完赛时间 -->
      <div class="bg-white border-2 border-cyan-200 rounded-lg p-6">
        <div class="flex items-center justify-between mb-2">
          <span class="text-sm font-semibold text-gray-600">完赛时间</span>
          <span class="text-xs bg-cyan-100 text-cyan-700 px-3 py-1 rounded-full">
            预计
          </span>
        </div>
        <div class="text-5xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-cyan-600 to-blue-600">
          {{ result.finishTime }}
        </div>
        <div class="text-sm text-gray-500 mt-2">
          {{ result.finishHours }} 小时 {{ result.finishMinutes }} 分 {{ result.finishSeconds }} 秒
        </div>
      </div>

      <!-- 说明文字 -->
      <div class="bg-gray-50 rounded-lg p-5">
        <div class="flex items-start gap-3">
          <span class="text-2xl">💡</span>
          <p class="text-sm text-gray-600 leading-relaxed">
            {{ result.description }}
          </p>
        </div>
      </div>

      <!-- 分享提示 -->
      <div class="text-center pt-4">
        <p class="text-xs text-gray-400">
          坚持训练，突破自我 🏆
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  result: Object,
  loading: Boolean,
  error: String
})
</script>
