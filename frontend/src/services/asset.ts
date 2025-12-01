// src/services/asset.ts
import api from './api';
import type { CryptoAssetResponse } from '../types/asset-types';

export interface AssetQueryParams {
  symbol?: string;
  name?: string;
  minPrice?: number;
  maxPrice?: number;
  page?: number;
  size?: number;
}

export async function getAssets(params: AssetQueryParams = {}): Promise<CryptoAssetResponse[]> {
  const response = await api.get('/api/crypto/assets', { params });
  return response.data.content; // Spring Data Page object
}
