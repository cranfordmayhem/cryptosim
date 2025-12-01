import { Role } from '../types/user-roles'

export interface Request {
    email: string;
    password: string;
}

export interface Login {
    id: number;
    username: string;
    role: Role;
}

export interface Token {
    accessToken: string;
}

export interface RefreshToken {
    refreshToken: string;
}