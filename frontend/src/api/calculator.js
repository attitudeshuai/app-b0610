import axios from 'axios'

const API_BASE_URL = import.meta.env.PROD 
  ? '/api' 
  : 'http://localhost:8080/api'

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 10000
})

// 响应拦截器
apiClient.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API 错误:', error)
    return Promise.reject(error)
  }
)

/**
 * 根据目标时间计算配速
 */
export const calculatePaceFromTime = (data) => {
  return apiClient.post('/pace/calculate-from-time', data)
}

/**
 * 根据配速计算完赛时间
 */
export const calculateTimeFromPace = (data) => {
  return apiClient.post('/pace/calculate-from-pace', data)
}

export default {
  calculatePaceFromTime,
  calculateTimeFromPace
}
