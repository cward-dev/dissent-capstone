ARG API_URL=https://dev10-capstone-team1.azurewebsites.net

# Base

FROM node:12-alpine as base
ENV NODE_ENV=production
WORKDIR /app
COPY package*.json ./
RUN npm ci && npm cache clean --force
ENV PATH /app/node_modules/.bin:$PATH

# Build

FROM base as build
# Need to define the env vars for the React app
# so that the build process will have access
# to the values to use for production
ENV REACT_APP_API_URL=$API_URL
WORKDIR /app
COPY . .
RUN npm run build

# Production

FROM nginx:stable-alpine as prod
COPY --from=build /app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY /nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]