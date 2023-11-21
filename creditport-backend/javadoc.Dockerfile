FROM gradle:8.4.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

COPY build.gradle settings.gradle /home/gradle/src/

COPY . .

RUN gradle javadoc


FROM nginx:1.25.3-alpine

COPY --from=build /home/gradle/src/build/docs/javadoc /usr/share/nginx/html
