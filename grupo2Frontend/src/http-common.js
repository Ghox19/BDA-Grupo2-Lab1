import axios from 'axios';

const httpClient = axios.create({
    baseURL: process.env.VITE_API_BASE_URL,
    headers: {
        'Content-type': 'application/json'
    }
});

export default httpClient;