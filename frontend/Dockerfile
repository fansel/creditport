FROM node:latest as build

WORKDIR /app

COPY frontend/package*.json ./

RUN npm ci --quiet

COPY . .

RUN npm run build

FROM node:latest

WORKDIR /app
ENV NODE_ENV production

COPY --from=build /app/build build/
COPY --from=build /app/node_modules node_modules/
COPY --from=build /app/package*.json ./

EXPOSE 3000
CMD ["node", "build/index.js"]
