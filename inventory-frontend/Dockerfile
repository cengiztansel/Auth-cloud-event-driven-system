## Development & Test level Dockerfile
## 1. Aşama: Build Aşaması (Node.js kullanarak React kodunu derleme)
#FROM node:20-alpine AS build
#WORKDIR /app
## Önce paket dosyalarını kopyalayıp bağımlılıkları yüklüyoruz (Cache avantajı için)
#COPY package*.json ./
#RUN npm install
## Tüm kodu kopyalayıp "dist" klasörünü oluşturuyoruz
#COPY . .
#RUN npm run build

## 2. Aşama: Serve Aşaması (Level 1'de öğrendiğimiz Nginx ile sunma)
#FROM nginx:alpine
## Node.js'in ürettiği statik dosyaları Nginx'in yayın klasörüne kopyalıyoruz
#COPY --from=build /app/dist /usr/share/nginx/html
## React'in router (sayfa yönlendirmeleri) yapısının bozulmaması için Nginx ayarı
#RUN echo "server { listen 80; location / { root /usr/share/nginx/html; index index.html index.htm; try_files \$uri \$uri/ /index.html; } }" > /etc/nginx/conf.d/default.conf

#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]

## Production level Dockerfile
FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 5173
# Vite'ın dış dünyadan erişilebilir olması için --host ekliyoruz
CMD ["npm", "run", "dev", "--", "--host"]
