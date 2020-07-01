FROM node:lts-alpine as builder

WORKDIR /app
COPY ./package.json /app/
COPY ./package-lock.json /app/
RUN npm install --no-progress

COPY ./ /app/
RUN npm run build

FROM nginx:latest

COPY --from=builder /app/build /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf.template

COPY ./scripts/docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 80