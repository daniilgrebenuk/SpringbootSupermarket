import {User} from "./user";

export interface Employee{
  id: number
  name: string;
  surname: string;
  imageUrl: string;
  position: string;
  user: User;
}
