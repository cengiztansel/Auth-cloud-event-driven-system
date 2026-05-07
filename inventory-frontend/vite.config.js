import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
/*
    server: {
      allowedHosts: ["cengiztansel.duckdns.org"], // Bu satırı ekle
      host: true, // Dış dünyaya açılması için şart
      port: 5173
    }
*/
    server: {
      host: '0.0.0.0',
      port: 5173,
      strictPort: true,
      allowedHosts: true // Tüm hostlara izin ver (Test aşaması için en garantisi budur)
      // veya allowedHosts: ["cengiztansel.duckdns.org"]
    }

})
