export interface Page<T> {
  content: T[],
  number: number;
  size: number,
  totalPages: number;
  totalElements: number;
}
