import { Login, Request, Token, RefreshToken  } from "../types/auth-types";
import {request} from "./api-request";

const BASE_URL = "http://localhost:8080/auth";

export async function login(payload: Request): Promise<Login> {
    const data = await request<Login>(`${BASE_URL}/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload),
        credentials: "include",
    });

    return data;
}

export async function logout(): Promise<void> {
    return request<void>(`${BASE_URL}/logout`, {
        method: "GET",
        credentials: "include"
    });
}

export async function refreshAccessToken(refreshToken: string): Promise<Token>{
    return request<Token>(`${BASE_URL}/refresh`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({refreshToken}),
        credentials: "include"
    });
}