export const enabledFormats = ['bold', 'italic', 'code', 'blockquote', 'mention'];

const displayMentionModule = {
  allowedChars: /^[A-Za-z\s]*$/,
  mentionDenotationChars: ['@', '#'],
  minChars: 3,
  fixMentionsToQuill: true,
};

export const displayModules = {
  mention: displayMentionModule,
  toolbar: null,
};

export type RenderList = (values: any[], searchTerm?: string) => void;

export type MentionSource = (
    searchTerm: string, renderList: RenderList, mentionChar: string) => void;

export type MentionRenderItem = (item: any, searchTerm?: string) => string;

const getEditorMentionModule = (source: MentionSource, mentionRenderItem: MentionRenderItem) => ({
  ...displayMentionModule,
  source,
  renderItem: mentionRenderItem,
});

export const getEditorModules = (source: MentionSource, renderItem: MentionRenderItem) => ({
  ...displayModules,
  mention: getEditorMentionModule(source, renderItem),
});
