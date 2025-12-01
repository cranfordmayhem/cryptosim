<!-- src/views/LoginView.vue -->
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../services/auth'
import { useAuthStore } from '../stores/auth'

const email = ref('')
const password = ref('')
const error = ref('')

const router = useRouter()
const authStore = useAuthStore()


const handleLogin = async () => {
  error.value = ''
  try {
    const user = await login({ email: email.value, password: password.value })
    authStore.setUser(user.role, user.id)
    router.push('/dashboard')
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Login failed'
  }
}

console.log(authStore.userId)
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 p-4">
    <form @submit.prevent="handleLogin" class="w-full max-w-md bg-white p-6 rounded-lg shadow">
      <h2 class="text-2xl font-bold mb-4">Login</h2>

      <div class="mb-4">
        <input v-model="email" type="email" placeholder="Email" class="w-full border p-2 rounded" required />
      </div>
      <div class="mb-4">
        <input v-model="password" type="password" placeholder="Password" class="w-full border p-2 rounded" required />
      </div>

      <div v-if="error" class="mb-4 text-red-500">{{ error }}</div>

      <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
        Login
      </button>
    </form>
  </div>
</template>
