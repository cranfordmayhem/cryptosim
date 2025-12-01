<!-- src/components/AssetChart.vue -->
<script setup lang="ts">
import { defineProps, computed } from 'vue'
import VueApexCharts from 'vue3-apexcharts'

interface PriceHistory {
  date: string
  price: number
}

interface Holding {
  assetName: string
  priceHistory: PriceHistory[]
}

const props = defineProps<{ holdings: Holding[] }>()



// Transform holdings into ApexCharts series
const series = computed(() =>
  (props.holdings || []).map(h => ({
    name: h.assetName,
    data: (h.priceHistory || []).map(p => ({ x: p.date, y: p.price }))
  }))
)


const chartOptions = {
  chart: { id: 'price-history', type: 'line', zoom: { enabled: true } },
  xaxis: { type: 'datetime' },
  yaxis: { title: { text: 'Price (USD)' } },
  tooltip: { x: { format: 'dd MMM yyyy' } },
  stroke: { curve: 'smooth' }
}
</script>

<template>
  <div class="w-full overflow-x-auto">
    <VueApexCharts type="line" :options="chartOptions" :series="series" height="350" />
  </div>
</template>
