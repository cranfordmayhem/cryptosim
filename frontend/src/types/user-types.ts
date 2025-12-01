import { Role } from "../types/user-roles"

export interface UserAccount  {
    id?: number;
    email?: string;
    role?: Role;
}

export interface UserFilter {
    page?: number;
    size?: number;
}