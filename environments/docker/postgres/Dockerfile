FROM postgres:16.10-bookworm

ENV LANG_NAME pt_BR
ENV LANG_ENCODING UTF-8
ENV LANG pt_BR.utf8

RUN localedef -i $LANG_NAME -c -f $LANG_ENCODING -A /usr/share/locale/locale.alias $LANG_NAME.$LANG_ENCODING
