import React from 'react';
import ReactQuill from 'react-quill';
import QuillMention from 'quill-mention';
import 'react-quill/dist/quill.core.css';
import 'quill-mention/dist/quill.mention.min.css';
import '../quill.css';
import { enabledFormats, getEditorModules, RenderList } from 'constants/quill';
import { searchHashtagDebounced } from 'api/hashtag/api';
import { searchUsersByNameDebounced } from 'api/users/api';
import { HashtagResponse } from 'api/hashtag/types';
import { UserSearchResponse } from 'api/users/types';

ReactQuill.Quill.register('modules/mention', QuillMention);

const searchFn = (c: string) => (
  c === '@' ? searchUsersByNameDebounced : searchHashtagDebounced
);

const sourceFunction = async (searchTerm: string, renderList: RenderList, mentionChar: string) => {
  if (searchTerm.length > 0) {
    const values = await searchFn(mentionChar)(searchTerm);
    renderList(values, searchTerm);
  }
};

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

const Editor = React.forwardRef<ReactQuill, EditorProps>((
  {
    state, onStateChange, onFocus, onBlur,
  }: EditorProps, ref,
) => (
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
));

export default Editor;
