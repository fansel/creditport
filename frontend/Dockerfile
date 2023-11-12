# development
FROM public.ecr.aws/docker/library/node:lts-alpine as development
WORKDIR /app
COPY package*.json ./ 

RUN npm ci --quiet

EXPOSE 5173
CMD ["npm", "run", "dev", "--", "--host"]

# build
FROM development as build
COPY . .
RUN npm run build

# production
FROM build as production
COPY --from=build /app/build build/
COPY --from=build /app/node_modules node_modules/
COPY --from=build /app/package*.json ./

EXPOSE 3000
CMD ["node","build/index.js"]
