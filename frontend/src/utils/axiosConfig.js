import axios from "axios";
import { BrowserRouter } from "react-router";

// Add a request interceptor
axios.interceptors.request.use(
  function(config) {
    const token = localStorage.getItem("authorization");

    if (token != null) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (window.location.pathname != "/") {
      window.location.replace("/");
    }

    console.log("Config:");
    console.log(config);
    return config;
  },
  function(error) {
    alert(1);
    return Promise.reject(error);
  }
);

// Add a response interceptor
axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    console.log("Response: ");
    console.log(response);
    return response;
  },
  function(error) {
    // Do something with response error
    if (error.response.status === 401 && window.location.pathname != "/") {
      window.location.replace("/");
    }
    return Promise.reject(error);
  }
);
