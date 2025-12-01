// src/types/asset-types.ts
export interface PriceHistory {
  date: string;
  price: number;
}

export interface CryptoAssetResponse {
  id: string;
  symbol: string;
  name: string;
  currentPrice: number;
  priceHistory: PriceHistory[];
}
