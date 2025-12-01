// src/stores/auth.ts
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: false,
    userRole: localStorage.getItem('userRole') || null,
    userId: localStorage.getItem('userId')
      ? Number(localStorage.getItem('userId'))
      : null,
  }),

  actions: {
    setUser(role: string, id: number) {
      this.isLoggedIn = true
      this.userRole = role
      this.userId = id

      // Persist
      localStorage.setItem('userRole', role)
      localStorage.setItem('userId', String(id))
    },

    logout() {
      this.isLoggedIn = false
      this.userRole = null
      this.userId = null

      // Remove from memory
      localStorage.removeItem('userRole')
      localStorage.removeItem('userId')
    }
  }
})
