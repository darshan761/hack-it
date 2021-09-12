import axios from 'axios';

export default axios.create({
    baseURL: "https://hack-it-backend.herokuapp.com/api/v1/",
    headers: {
        "Content-type": "application/json"
    }
});