import { useRef } from 'react';
import ReactQuill from 'react-quill-new';
import 'quill-mention/autoregister';
import { displayModules, enabledFormats } from '../constants';

interface PostDisplayProps {
  // biome-ignore lint/suspicious/noExplicitAny: quill stuff
  postDelta: Record<string, any>;
}

// TODO: Add mention click handlers

export default function PostDisplay({ postDelta }: PostDisplayProps) {
  const editorRef = useRef<ReactQuill>(null);

  return (
    <ReactQuill
      ref={editorRef}
      // biome-ignore lint/suspicious/noExplicitAny: quill stuff
      value={postDelta as any}
      formats={enabledFormats}
      modules={displayModules}
      readOnly
    />
  );
}
