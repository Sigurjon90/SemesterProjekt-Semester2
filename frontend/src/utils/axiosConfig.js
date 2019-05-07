import axios from "axios";
import { BrowserRouter } from "react-router";

// Add a request interceptor
axios.interceptors.request.use(
  function (config) {
    const token = "eyJjaXRpemVucyI6WyIyNTU2ZTU2Yi1mNWVlLTQ1ZGItYWRlYS01MjE5MTRmN2YxYjciXSwiY2FyZWNlbnRlciI6ImJmOWZjOTc1LTE0YjktNDFmMy04YmY0LWE1ZmYwNGZlMGU2NCIsImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJkdXBlciIsImp0aSI6ImFhY2Q0Mjc5LTc2ZTUtNDE3Yy04NjExLTNhNDgwOGJhYmNkZSIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW4iXSwiaWF0IjoxNTU2OTY4Nzc1LCJleHAiOjE1NTcwNTUxNzV9.TgMkupMBOxBIaD1zYPhhjrevgcnnJlBXc98SFEp6RXQJZzMHrXw1EZs9km7kxEUCaheWlXy6e9tBQureQdYnyA"
    
    if (token != null) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (window.location.pathname != "/") {
      window.location.replace("/");
    }

    return config;
  },
  function (error) {
    alert(1);
    return Promise.reject(error);
  }
);

// Add a response interceptor
axios.interceptors.response.use(
  function (response) {
    // Do something with response data
    return response;
  },
  function (error) {
    // Do something with response error
    if (error.response.status === 401 && window.location.pathname != "/") {
      window.location.replace("/");
    }
    return Promise.reject(error);
  }
);
