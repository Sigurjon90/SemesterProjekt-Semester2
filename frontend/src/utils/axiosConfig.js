import axios from "axios";
import { BrowserRouter } from "react-router";

// Add a request interceptor
axios.interceptors.request.use(
  function (config) {
    const token = "eyJjaXRpemVucyI6WyI2ZTU5NzFiMy05OWEwLTQ5MGItYmViZS02ODlhMTFhZTk5MTciLCJiMzEwZjYzNC04N2ZhLTRhYzQtOTljMi02Njk1YjViM2QzZWUiLCI5ZDZiZTQwZS1hOWRkLTRhOTYtOWQwNy1kZDlmYTBkY2FhODciLCIzMjM3MjkxMS1kN2ZiLTRlMjctYTAzZC1jNmMwMmU2MWUxZDgiLCJhNzM0NTA3Zi1kNGMzLTQ3ZTktODhhZi0zZDI4MGJhZjMzNTMiXSwiY2FyZWNlbnRlciI6ImJmOWZjOTc1LTE0YjktNDFmMy04YmY0LWE1ZmYwNGZlMGU2NCIsImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJkdXBlciIsImp0aSI6ImFhY2Q0Mjc5LTc2ZTUtNDE3Yy04NjExLTNhNDgwOGJhYmNkZSIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW4iXSwiaWF0IjoxNTU3MDQwNjY2LCJleHAiOjE1NTcxMjcwNjZ9.0s6TB-QQzTb1OSlcWf9R6Id9jcEKTm8TzvEyEyx2LjSbsMhfFaZTlLQpl9zGdzzh057kELWch9_GlY-lwtmbYw"

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
