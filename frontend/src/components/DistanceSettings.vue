<template>
  <div v-if="isOpen" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 p-4">
    <div class="bg-white rounded-2xl shadow-2xl max-w-md w-full p-6 animate-fade-in">
      <!-- 标题 -->
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-2xl font-bold text-gray-800 flex items-center gap-2">
          ⚙️ 距离设置
        </h3>
        <button
          @click="close"
          class="text-gray-400 hover:text-gray-600 transition-colors"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- 说明 -->
      <p class="text-sm text-gray-600 mb-6">
        设置自定义马拉松距离，留空则使用标准距离
      </p>

      <!-- 表单 -->
      <form @submit.prevent="save" class="space-y-6">
        <!-- 全程马拉松距离 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">
            全程马拉松距离（公里）
          </label>
          <input
            v-model.number="form.fullDistance"
            type="number"
            step="0.001"
            min="1"
            max="200"
            class="input-field"
            placeholder="默认: 42.195"
          />
          <p class="text-xs text-gray-500 mt-1">
            标准距离：42.195 公里
          </p>
        </div>

        <!-- 半程马拉松距离 -->
        <div>
          <label class="block text-sm font-semibold text-gray-700 mb-2">
            半程马拉松距离（公里）
          </label>
          <input
            v-model.number="form.halfDistance"
            type="number"
            step="0.001"
            min="1"
            max="200"
            class="input-field"
            placeholder="默认: 21.0975"
          />
          <p class="text-xs text-gray-500 mt-1">
            标准距离：21.0975 公里
          </p>
        </div>

        <!-- 按钮组 -->
        <div class="flex gap-3">
          <button
            type="button"
            @click="reset"
            class="btn-secondary flex-1"
          >
            🔄 重置为标准
          </button>
          <button
            type="submit"
            class="btn-primary flex-1"
          >
            💾 保存设置
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  isOpen: Boolean,
  settings: Object
})

const emit = defineEmits(['close', 'save'])

const form = reactive({
  fullDistance: null,
  halfDistance: null
})

// 监听设置变化，更新表单
watch(() => props.settings, (newSettings) => {
  if (newSettings) {
    form.fullDistance = newSettings.fullDistance || null
    form.halfDistance = newSettings.halfDistance || null
  }
}, { immediate: true })

const save = () => {
  emit('save', {
    fullDistance: form.fullDistance || null,
    halfDistance: form.halfDistance || null
  })
}

const reset = () => {
  form.fullDistance = null
  form.halfDistance = null
  emit('save', {
    fullDistance: null,
    halfDistance: null
  })
}

const close = () => {
  emit('close')
}
</script>
