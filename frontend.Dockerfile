FROM fischerscode/flutter:master as BUILD_STAGE

ARG API_URL
ARG API_PORT
ARG GOOGLE_CLIENT_ID

ENV API_URL $API_URL
ENV API_PORT $API_PORT
ENV GOOGLE_CLIENT_ID $GOOGLE_CLIENT_ID

RUN useradd -ms /bin/bash flutteruser

WORKDIR frontend

ADD flutter_frontend frontend

RUN chown -R flutteruser: /app
USER flutteruser

RUN flutter config --enable-web
RUN flutter clean
RUN flutter pub get
RUN flutter build web --web-renderer html --release --dart-define=API_URL=${API_URL} --dart-define=API_PORT=${API_PORT} --dart-define=GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}

FROM nginx:latest as DEPLOY_STAGE

COPY --from=BUILD_STAGE /frontend/build/web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]