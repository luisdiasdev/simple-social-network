import axios from 'axios';
import { publicApi } from 'client/http';
import { PostPictureResponse, PostResponse, PostCreateRequest } from './types';


const ENDPOINT = '/posts';
const PICTURE_ENDPOINT = `${ENDPOINT}/picture`;

export const savePostPicture = (file: File) => {
  const formData = new FormData();
  formData.set('file', file);

  const source = axios.CancelToken.source();

  const promise = publicApi.post<PostPictureResponse>(PICTURE_ENDPOINT, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    cancelToken: source.token,
  })
    .then((response) => response.data);

  return {
    cancellationSource: source,
    promise,
  };
};

export const deletePostPicture = (uuid: string) => publicApi.delete(`${PICTURE_ENDPOINT}/${uuid}`);

export const savePost = (request: PostCreateRequest) => publicApi
  .post<PostResponse>(ENDPOINT, request)
  .then((response) => response.data);

export const deletePost = (id: number) => publicApi.delete(`${ENDPOINT}/${id}`);
