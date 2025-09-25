export type LoginData = {
  username: string;
  password: string;
};

export interface Page<T> {
  content: T[];
  number: number;
  size: number;
  totalPages: number;
  totalElements: number;
}
