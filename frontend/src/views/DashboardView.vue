<template>
  <div class="min-h-screen flex flex-col">
    <!-- Header -->
    <header class="bg-gray-800 text-white p-4 flex justify-between items-center">
      <h1 class="text-xl font-bold">Crypto Dashboard</h1>
      <button @click="logout" class="bg-red-500 hover:bg-red-600 px-4 py-2 rounded">Logout</button>
    </header>

    <div class="flex flex-1 flex-col md:flex-row">
      <!-- Sidebar -->
      <aside class="bg-gray-100 md:w-64 p-4">
        <ul class="space-y-2">
          <li>
            <button @click="activeTab = 'assets'" :class="tabClass('assets')">Assets Chart</button>
          </li>
          <li>
            <button @click="activeTab = 'portfolio'" :class="tabClass('portfolio')">Portfolio</button>
          </li>
        </ul>
      </aside>

      <!-- Main Content -->
      <main class="flex-1 p-4 overflow-y-auto">
        <!-- Assets Tab -->
        <div v-if="activeTab === 'assets'">
          <table class="w-full border-collapse mb-6">
            <thead>
              <tr class="bg-gray-200">
                <th class="border p-2">Name</th>
                <th class="border p-2">Symbol</th>
                <th class="border p-2">Current Price (USD)</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="asset in assets" :key="asset.id">
                <td class="border p-2">
                  <button
                    class="text-blue-500 hover:underline"
                    @click="selectAsset(asset)"
                  >
                    {{ asset.name }}
                  </button>
                </td>
                <td class="border p-2">{{ asset.symbol }}</td>
                <td class="border p-2">
                  {{ asset.priceUsd !== null ? `$${asset.priceUsd.toFixed(2)}` : 'N/A' }}
                </td>
              </tr>
            </tbody>
          </table>

          <!-- Show chart only if an asset is selected -->
          <div v-if="selectedAsset">
            <h2 class="text-lg font-bold mb-2">{{ selectedAsset.name }} Price Chart</h2>
            <AssetChart
              :asset-name="selectedAsset.name"
              :price-history="selectedAsset.priceHistory || []"
            />
            <button
              class="mt-4 bg-gray-300 px-3 py-1 rounded hover:bg-gray-400"
              @click="selectedAsset = null"
            >
              Close Chart
            </button>
          </div>
        </div>

        <!-- Other Tabs -->
        <UserProfileForm v-if="activeTab === 'profile'" />
        <PortfolioForm v-if="activeTab === 'portfolio'" />
        <PortfolioHoldingsForm v-if="activeTab === 'holdings'" />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'
import AssetChart from '../components/AssetChart.vue'
import PortfolioForm from '../components/PortfolioForm.vue'
import { getAssets } from '../services/asset'
import type { CryptoAssetResponse } from '../types/asset-types'

const activeTab = ref<'assets'|'profile'|'portfolio'|'holdings'>('assets')
const authStore = useAuthStore()
const router = useRouter()

console.log(authStore.userId)

const assets = ref<CryptoAssetResponse[]>([])
const selectedAsset = ref<CryptoAssetResponse | null>(null)

async function fetchAssets() {
  try {
    const response = await getAssets({ page: 0, size: 50 })
    assets.value = response.map(a => ({ ...a, priceUsd: a.priceUsd ?? null }))
  } catch (err) {
    console.error('Failed to fetch assets:', err)
  }
}

function selectAsset(asset: CryptoAssetResponse) {
  selectedAsset.value = asset
}

function logout() {
  authStore.logout()
  router.push('/login')
}

function tabClass(tab: string) {
  return [
    'block w-full text-left px-3 py-2 rounded hover:bg-gray-300',
    activeTab.value === tab ? 'bg-gray-300 font-bold' : ''
  ].join(' ')
}

watch(activeTab, (tab) => {
  if (tab === 'assets') fetchAssets()
})

onMounted(() => {
  if (activeTab.value === 'assets') fetchAssets()
})
</script>

<style scoped>
aside {
  overflow-y: auto;
}
</style>
