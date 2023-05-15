FROM cirrusci/flutter:stable as BUILD_STAGE

ADD flutter_frontend frontend

WORKDIR frontend

RUN flutter config --enable-web
RUN flutter build web --web-renderer html --release --dart-define=API_URL=$API_URL --dart-define=API_PORT=$API_PORT

FROM nginx:latest as DEPLOY_STAGE

COPY --from=BUILD_STAGE /frontend/build/web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]