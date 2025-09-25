export type PostPictureResponse = {
  uuid: string;
  contentUri: string;
};

export type ImageWithPreview = {
  file: File;
  preview: string;
  localUuid?: string;
  remoteUuid?: string;
  promise?: Promise<PostPictureResponse>;
  hasError: boolean;
  error?: Error;
};

export type PostCreateRequest = {
  // biome-ignore lint/suspicious/noExplicitAny: todo
  message: any;
  pictures: string[];
};

export type PostResponse = {
  id: number;
  userId: number;
  // biome-ignore lint/suspicious/noExplicitAny: todo
  message: any;
  pictures: string[];
  createdAt: string;
};
