export type LoginData = {
  username: string;
  password: string;
};

export interface Page<T> {
  content: T[];
  page: {
    size: number;
    number: number;
    totalPages: number;
    totalElements: number;
  };
}
