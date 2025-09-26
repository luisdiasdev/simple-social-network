import ReactQuill from 'react-quill-new';
import 'quill-mention/autoregister';
import 'react-quill-new/dist/quill.core.css';
import 'quill-mention/dist/quill.mention.min.css';
import '../quill.css';
import React from 'react';
import { type HashtagResponse, searchHashtagDebounced } from '../../../api/hashtag';
import { searchUsersByNameDebounced, type UserSearchResponse } from '../../../api/users';
import { enabledFormats, getEditorModules, type RenderList } from '../constants';

const searchFn = (c: string) => (c === '@' ? searchUsersByNameDebounced : searchHashtagDebounced);

const sourceFunction = async (searchTerm: string, renderList: RenderList, mentionChar: string) => {
  if (searchTerm.length > 0) {
    const values = await searchFn(mentionChar)(searchTerm);

    if (values) {
      renderList(values, searchTerm);
    }
  }
};

// biome-ignore lint/suspicious/noExplicitAny: quill things
const handleChange = (ref: any, onStateChange: (value: string) => void) => (state: string) => {
  const maxMessageLength = 1000;
  const length = ref?.current?.getEditor().getLength();
  const newState = state;
  if (length && length > maxMessageLength) {
    // eslint-disable-next-line no-unused-expressions
    ref?.current?.getEditor().deleteText(maxMessageLength - 1, length);
    return;
  }
  onStateChange(newState);
};

const renderItem = (item: UserSearchResponse & HashtagResponse) => {
  if (item.displayName) {
    return `<strong>@${item.value}</strong>&nbsp;&nbsp;&nbsp;<em>${item.displayName}</em>`;
  }
  return `<em>#${item.value}</em>`;
};

interface EditorProps {
  state: string;
  onStateChange: (value: string) => void;
  onFocus?: () => void;
  onBlur?: () => void;
}

const Editor = React.forwardRef<ReactQuill, EditorProps>(
  ({ state, onStateChange, onFocus, onBlur }: EditorProps, ref) => (
    <ReactQuill
      ref={ref}
      value={state}
      onChange={handleChange(ref, onStateChange)}
      onFocus={onFocus}
      onBlur={onBlur}
      formats={enabledFormats}
      modules={getEditorModules(sourceFunction, renderItem)}
      placeholder="What's on your mind?"
    />
  ),
);

export default Editor;
