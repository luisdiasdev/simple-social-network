import { useDropzone } from 'react-dropzone';
import { MAX_FILE_SIZE } from 'constants/upload';

interface DefaultDropzoneConfig {
  onSelect: (files: File[]) => void;
  onReject?: (files: File[]) => void;
}

export const useDefaultDropzone = ({ onSelect, onReject }: DefaultDropzoneConfig) => useDropzone({
  noDrag: true,
  multiple: true,
  accept: 'image/*',
  onDropAccepted: onSelect,
  onDropRejected: onReject,
  maxSize: MAX_FILE_SIZE,
});
