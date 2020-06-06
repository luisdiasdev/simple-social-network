import React, { useRef } from 'react';
import ReactQuill from 'react-quill';
import QuillMention from 'quill-mention';
import { enabledFormats, displayModules } from '../../../constants/quill';

ReactQuill.Quill.register('modules/mention', QuillMention);

interface PostDisplayProps {
    postDelta: Record<string, any>
}

// TODO: Add mention click handlers

export default function PostDisplay({ postDelta }: PostDisplayProps) {
  const editorRef = useRef<ReactQuill>(null);

  return (
    <ReactQuill
      ref={editorRef}
      value={postDelta as any}
      formats={enabledFormats}
      modules={displayModules}
      readOnly
    />
  );
}
