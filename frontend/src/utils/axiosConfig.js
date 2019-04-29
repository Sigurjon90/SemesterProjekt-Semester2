import axios from "axios";
import { BrowserRouter } from "react-router";

// Add a request interceptor
axios.interceptors.request.use(
  function (config) {
    const token = "eyJjaXRpemVucyI6WyJlZDFmZTkxOC0wYTFiLTRmNGEtODJlOS1lZGNkYmQ5NGQ0ODciLCIyMTYxNmI1Yi03ZWEzLTRmODYtOWEyNS0wYmQzNzY2MDBjMjEiXSwiY2FyZWNlbnRlciI6ImJmOWZjOTc1LTE0YjktNDFmMy04YmY0LWE1ZmYwNGZlMGU2NCIsImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJkdXBlciIsImp0aSI6ImFhY2Q0Mjc5LTc2ZTUtNDE3Yy04NjExLTNhNDgwOGJhYmNkZSIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW4iXSwiaWF0IjoxNTU2NTQ4MzQyLCJleHAiOjE1NTY2MzQ3NDJ9.7hmiYwOdNawJPEPgZsKDJ4PSTa1pz6dWH0SN8Zj-zb-azaMEbVbKu8jlNlrE26MmyZSwL2FHSOgkDum014z3lw"

    if (token != null) {
      config.headers.Authorization = `Bearer ${token}`;
    } else if (window.location.pathname != "/") {
      window.location.replace("/");
    }

    console.log("Config:");
    console.log(config);
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
    console.log("Response: ");
    console.log(response);
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
