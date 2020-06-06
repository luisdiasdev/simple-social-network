export type PostPictureResponse = {
  uuid: string;
  contentUri: string;
}

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
  message: any;
  pictures: string[];
};

export type PostResponse = {
  id: number;
  userId: number;
  message: any;
  pictures: string[];
  createdAt: string;
};
