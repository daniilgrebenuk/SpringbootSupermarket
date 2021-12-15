export interface Role {
  id: number;
  authority: string;
}

export interface User{
  id: number;
  role: Role;
}
