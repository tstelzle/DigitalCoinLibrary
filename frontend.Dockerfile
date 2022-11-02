FROM cirrusci/flutter:stable as BUILD_STAGE

ADD frontend frontend

WORKDIR frontend

USER root
RUN flutter build web

FROM nginx:latest as DEPLOY_STAGE

COPY --from=BUILD_STAGE /frontend/build/web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]