import './App.css';
import { BrowserRouter , Routes, Route } from 'react-router';
import CreateUser from './components/user/CreateUser';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import ListUser from './components/user/ListUser';
import LoginPage from './components/authentification/LoginPage';
import ListProduct from './components/product/ListProduct';
import CreateProduct from './components/product/CreateProduct';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';



function App() {

  return (
   
    <div className="App">
        <ToastContainer />
        <BrowserRouter>
          <Routes>
             <Route path="/create/product" element ={<CreateProduct />}> </Route>
             <Route path="/products" element ={<ListProduct />}>></Route>
             <Route path="/login" element ={<LoginPage />}> </Route>
             <Route path="/users" element ={<ListUser />}> </Route>
             <Route path="/create/user" element ={<CreateUser />}> </Route>
          </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
