// src/services/auth.ts
import api from './api'

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  id: number
  role: string
}

export async function login(payload: LoginRequest): Promise<LoginResponse> {
  const res = await api.post<LoginResponse>('/auth/login', payload, { withCredentials: true })
  return res.data
}

export async function logout() {
  await api.post('/auth/logout', {}, { withCredentials: true })
}
