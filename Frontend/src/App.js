import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router";
import CreateUser from "./components/user/CreateUser";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import ListUser from "./components/user/ListUser";
import LoginPage from "./components/authentification/LoginPage";
import ListProduct from "./components/product/ListProduct";
import CreateProduct from "./components/product/CreateProduct";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import { createContext, useState } from "react";
import Users from "./graphql/user/Users";

export const Context = createContext();

function App() {
  const [loggedIn, setLoggedIn] = useState(true);

  return (
    <div className="App">
      <ToastContainer />
      <Context.Provider value={[loggedIn, setLoggedIn]}>
        <BrowserRouter>
          <Routes>
            <Route path="/list/users" element={<Users />}></Route>
            <Route path="/create/product" element={<CreateProduct />}></Route>
            <Route path="/products" element={<ListProduct />}></Route>
            <Route path="/login" element={<LoginPage />}></Route>
            <Route path="/users" element={<ListUser />}></Route>
            <Route path="/create/user" element={<CreateUser />}></Route>
          </Routes>
        </BrowserRouter>
      </Context.Provider>
    </div>
  );
}

export default App;
