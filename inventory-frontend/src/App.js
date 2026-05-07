import React, { useState } from 'react';
import keycloak from './keycloak';
//import Keycloak from 'keycloak-js';

console.log("Keycloak durumu:", keycloak.authenticated);

function App() {
  const [message, setMessage] = useState('');

  const addProduct = async () => {
    try {
      const response = await fetch('http://cengiztansel.duckdns.org:8080/api/products', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          // İşte sihir burada! Keycloak JS kütüphanesi token'ı bizim için arka planda tutuyor.
          'Authorization': `Bearer ${keycloak.token}`
        },
        body: JSON.stringify({ name: "React'ten Gelen Harika Urun", price: 2026.0 })
      });

      if (response.status === 201 || response.ok) {
        setMessage("Ürün başarıyla eklendi! 🎉 Arka plana bak, Kafka mesajı bile atmış olabilir!");
      } else {
        setMessage("Hata aldık, Kodu: " + response.status);
      }
    } catch (error) {
      console.error("Bağlantı hatası:", error);
    }
  };

  return (
    <div style={{ padding: '50px', fontFamily: 'sans-serif' }}>
      <h1>Envanter Sistemi - Frontend</h1>
      <p>Giriş yapan kullanıcı: <strong>{keycloak.tokenParsed?.preferred_username}</strong></p>

      <button onClick={addProduct} style={{ padding: '10px 20px', fontSize: '16px' }}>
        🚀 Test Ürünü Ekle
      </button>

      <p style={{ color: 'green', fontWeight: 'bold' }}>{message}</p>

      <br/><br/>
      <button onClick={() => keycloak.logout()}>Çıkış Yap</button>
    </div>
  );
}

export default App;
