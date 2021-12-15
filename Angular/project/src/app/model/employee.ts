import {User} from "./user";

export interface Employee{
  id: number;
  name: string;
  surname: string;
  pesel: number;
  phone: string;
  imageUrl: string;
  user: User;
  enrollmentDate: Date;
  contractInMonth: number;
  salary: number;
}
