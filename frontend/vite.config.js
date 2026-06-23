import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Configuração do Vite (inclui plugin React para JSX automático)
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000 // o front-end vai rodar em http://localhost:3000
  }
})
