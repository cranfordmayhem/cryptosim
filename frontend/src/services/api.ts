// src/services/api.ts
import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true // sends cookies automatically
})

// Optional: global response interceptor
api.interceptors.response.use(
  res => res,
  async err => {
    if (err.response?.status === 401) {
      console.warn('Unauthorized - token may have expired')
      // Optional: trigger refresh flow
    }
    return Promise.reject(err)
  }
)

export default api
