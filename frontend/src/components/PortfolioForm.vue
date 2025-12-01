<template>
  <div class="space-y-6">
    <h2 class="text-xl font-bold mb-2">User Portfolio</h2>

    <!-- User Account (read-only) -->
    <div class="border p-4 rounded bg-gray-50">
      <h3 class="font-semibold mb-2">User Account</h3>
      <p><strong>Email:</strong> {{ userAccount?.email || 'N/A' }}</p>
    </div>

    <!-- User Profile (editable) -->
    <div class="border p-4 rounded bg-gray-50">
      <h3 class="font-semibold mb-2">Profile</h3>
      <div class="mb-2">
        <label class="block font-medium">First Name</label>
        <input v-model="userProfileForm.firstName" type="text" class="w-full border rounded p-2" />
      </div>
      <div class="mb-2">
        <label class="block font-medium">Last Name</label>
        <input v-model="userProfileForm.lastName" type="text" class="w-full border rounded p-2" />
      </div>
      <div class="mb-2">
        <label class="block font-medium">Age</label>
        <input v-model.number="userProfileForm.age" type="number" class="w-full border rounded p-2" />
      </div>
    </div>

    <!-- Portfolio (editable) -->
    <div class="border p-4 rounded bg-gray-50">
      <h3 class="font-semibold mb-2">Portfolio</h3>
      <div class="mb-2">
        <label class="block font-medium">Portfolio Name</label>
        <input v-model="portfolioForm.name" type="text" class="w-full border rounded p-2" />
      </div>
    </div>

    <!-- Portfolio Holdings (read-only) -->
    <div class="border p-4 rounded bg-gray-50">
      <h3 class="font-semibold mb-2">Holdings</h3>
      <table class="w-full border-collapse">
        <thead>
          <tr class="bg-gray-200">
            <th class="border p-2">Asset</th>
            <th class="border p-2">Quantity</th>
            <th class="border p-2">Value (USD)</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="holding in holdings" :key="holding.id">
            <td class="border p-2">{{ holding.assetName }}</td>
            <td class="border p-2">{{ holding.quantity }}</td>
            <td class="border p-2">
              ${{ holding.value !== undefined ? holding.value.toFixed(2) : '0.00' }}
            </td>
          </tr>
          <tr v-if="holdings.length === 0">
            <td colspan="3" class="text-center p-2">No holdings found</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Save Button -->
    <button
      @click="saveChanges"
      class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600"
    >
      Save Changes
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import api from '../services/api'

interface UserAccount {
  id: number
  email: string
}

interface UserProfile {
  firstName: string
  lastName: string
  age: number
}

interface Portfolio {
  id: number
  name: string
}

interface PortfolioHolding {
  id: number
  assetName: string
  quantity: number
  value: number
}

const authStore = useAuthStore()

const userAccount = ref<UserAccount | null>(null)
const userProfile = ref<UserProfile | null>(null)
const portfolio = ref<Portfolio | null>(null)
const holdings = ref<PortfolioHolding[]>([])

// Form models
const userProfileForm = reactive<UserProfile>({
  firstName: '',
  lastName: '',
  age: 0
})

const portfolioForm = reactive<Portfolio>({
  id: 0,
  name: ''
})

async function fetchPortfolio() {
  try {
    // 1. Get logged-in account
    const accountRes = await api.get('/auth/me')
    userAccount.value = accountRes.data

    const userId = accountRes.data.id

    // 2. Get profile
    const profileRes = await api.get(`/profile/me/${userId}`)
    userProfile.value = profileRes.data
    Object.assign(userProfileForm, profileRes.data)

    // 3. Get portfolio + holdings from your backend combined endpoint
    const portfolioRes = await api.get(`/portfolio/me/${userId}`)

    console.log(portfolioRes)

    portfolio.value = portfolioRes.data

    const holdingsRes = await api.get(`/portfolios/${portfolioRes.data.id}/holdings`)

    console.log(holdingsRes.data)

    holdings.value = holdingsRes.data

    if (portfolio.value) {
      Object.assign(portfolioForm, portfolio.value)
    }
  } catch (err) {
    console.error('Failed to fetch portfolio info:', err)
  }
}

async function saveChanges() {
  try {
    if (userProfile.value) {
      await api.put(`/profile/${userProfile.value.id}`, userProfileForm)
    }

    if (portfolio.value) {
      await api.put(`/portfolio/${portfolio.value.id}`, portfolioForm)
    }

    alert('Changes saved successfully!')
  } catch (err) {
    console.error('Failed to save changes:', err)
    alert('Failed to save changes.')
  }
}

onMounted(fetchPortfolio)
</script>
