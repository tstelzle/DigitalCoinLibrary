FROM fischerscode/flutter:master as BUILD_STAGE

ADD frontend frontend

WORKDIR frontend

USER root
RUN flutter build web

FROM nginx:latest as DEPLOY_STAGE

COPY --from=BUILD_STAGE /home/flutter/frontend/build/web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]