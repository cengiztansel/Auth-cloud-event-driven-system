import { useState, useEffect } from 'react'
import { getProducts, createProduct } from './services/api'
import './App.css'

function App() {
  const [products, setProducts] = useState([])
  const [newProduct, setNewProduct] = useState({ name: '', price: '', quantity: '' })

  // Sayfa yüklendiğinde ürünleri getir
  useEffect(() => {
    fetchProducts()
  }, [])

  const fetchProducts = async () => {
    try {
      const response = await getProducts()
      setProducts(response.data)
    } catch (error) {
      console.error("Ürünler çekilirken hata oluştu:", error)
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setNewProduct({ ...newProduct, [name]: value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault() // Sayfanın yenilenmesini engeller
    try {
      await createProduct(newProduct)
      setNewProduct({ name: '', price: '', quantity: '' }) // Formu temizle
      fetchProducts() // Listeyi güncelle
    } catch (error) {
      console.error("Ürün eklenirken hata oluştu:", error)
    }
  }

  return (
    <div className="App">
      <h1>Envanter Yönetim Sistemi</h1>
      
      <div className="form-container">
        <h2>Yeni Ürün Ekle</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="name" placeholder="Ürün Adı" value={newProduct.name} onChange={handleInputChange} required />
          <input type="number" name="price" placeholder="Fiyat (₺)" value={newProduct.price} onChange={handleInputChange} required />
          <input type="number" name="quantity" placeholder="Adet" value={newProduct.quantity} onChange={handleInputChange} required />
          <button type="submit">Ekle</button>
        </form>
      </div>

      <div className="list-container">
        <h2>Ürün Listesi</h2>
        {products.length === 0 ? (
          <p>Henüz ürün eklenmemiş.</p>
        ) : (
          <ul>
            {products.map((product) => (
              <li key={product.id}>
                <strong>{product.name}</strong> - {product.price} ₺ (Stok: {product.quantity})
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  )
}

export default App
