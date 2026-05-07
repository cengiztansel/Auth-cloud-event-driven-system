import axios from 'axios';

// Backend adresimiz
const API_URL = 'http://cengiztansel.duckdns.org:8080/api/products';
baseURL: import.meta.env.VITE_API_BASE_URL || "http://cengiztansel.duckdns.org:8080"

export const getProducts = () => {
    return axios.get(API_URL);
};

export const createProduct = (product) => {
    return axios.post(API_URL, product);
};
