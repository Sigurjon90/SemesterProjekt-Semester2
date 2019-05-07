import jwt_decode from "jwt-decode";
import React, { Component } from "react";

const hasRole = role => {
  const token = localStorage.getItem("authorization");
  if (token == null) {
    return false;
  }
  const userData = jwt_decode(token);
  console.log(userData);
  if (userData.authorities == role) {
    return true;
  }
  return false;
};

export const authorization = (WrappedComponent, allowedRoles) => {
  const role = "admin";
  if (role == "admin") {
    return <WrappedComponent />;
  } else {
    return <h1>No page for you!</h1>;
  }
};

export default hasRole;
