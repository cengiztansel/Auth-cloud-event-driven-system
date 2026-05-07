import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import React from 'react'
import ReactDOM from 'react-dom/client'
import keycloak from './keycloak' // Daha önce oluşturduğumuz keycloak.js dosyası

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)

const root = ReactDOM.createRoot(document.getElementById('root'));

// KEYCLOAK INIT KODU BURASI
keycloak.init({
  onLoad: 'login-required', // Sayfa açılır açılmaz login zorunlu olsun
  checkLoginIframe: false,   // Geliştirme aşamasında (localhost) çakışmaları önlemek için
  pkceMethod: 'S256'          // Güvenlik standardını netleştirelim
}).then((authenticated) => {

  if (authenticated) {
    // Sadece kullanıcı giriş yaptıysa uygulamayı render et
    root.render(
      <React.StrictMode>
        <App />
      </React.StrictMode>
    );
  } else {
    // Eğer bir şekilde giriş başarısızsa sayfayı yenile (Keycloak otomatik yönlendirir)
    window.location.reload();
  }

}).catch((error) => {
  console.error("Keycloak Başlatılamadı:", error);
  // Hata durumunda ekrana bir mesaj basabilirsin
  root.render(<div>Güvenlik servisi şu an ulaşılamaz durumda. Lütfen bekleyin...</div>);
});