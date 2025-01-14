import React, { useState } from 'react';
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import ProductModal from './ProductModal';
import Cart from '../Cart/Cart';


const ListProduct = () => {

  const [products , productdatachange] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [nameFilter, setNameFilter] = useState(""); 
  const [inventoryFilter, setInventoryFilter] = useState("INSTOCK"); 
  const [pageSize, setPageSize] = useState(10); 

  const [selectedProduct, setSelectedProduct] = useState(null);
  const [quantity, setQuantity] = useState(sessionStorage.getItem('productsQuantityCart'));
  const [showModal, setShowModal] = useState(false);

  const [showCart, setShowCart] = useState(false);



   useEffect(() => {
       sessionStorage.setItem('productsQuantityCart', quantity);
      let token = sessionStorage.getItem('token');
      fetch(`http://localhost:9999/api/v1/products?nameProduct=${nameFilter}&page=${currentPage}&size=${pageSize}&inventory=${inventoryFilter}`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
            "content-type": "application/json"
        }
      }).then((res) => {
        return res.json();
      }).then((resp) => {
            productdatachange(resp);
            console.log("resp ", resp);
            console.log("total pages ", resp.totalPages);
            console.log("total elements  ", resp.totalElements);
            console.log("number page ", resp.pageable.pageNumber);
            console.log("page size ", resp.pageable.pageSize);
            setTotalPages(resp.totalPages);
      }).catch((err) => {
            console.log(err.message);
      }) 
     }, [currentPage, nameFilter, inventoryFilter, pageSize,quantity])







    const handlePageChange = (page) => {
        if (page >= 0 && page < totalPages) {
        setCurrentPage(page);
        }
    };


    const handleAddToCart = (product) => {
        setSelectedProduct(product);
        setQuantity(1);
        setShowModal(true);
    };

    const handleAddToCartConfirm = () => {
        console.log("Product added to cart:", selectedProduct);
        console.log("Quantity:", quantity);
        setShowModal(false);
    };


    return (
        <div>
            <div className="container">
                <div className="card">
                    <div className="card-title">
                        <h2>Products</h2>
                        <nav className="navbar">
                            {/* <button className="cart-button">
                                🛒 Panier (0)
                            </button> */}
                            <Cart></Cart>

                            <Button style={{ width: "3rem", height: "3rem", position: "relative" }}
                                    variant="outline-primary"
                                    className="rounded-circle"
                                    onClick={() => setShowCart(true)}
                                   >
                                    <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 576 512"
                                    fill="currentColor"
                                    >
                                    <path d="M96 0C107.5 0 117.4 8.19 119.6 19.51L121.1 32H541.8C562.1 32 578.3 52.25 572.6 72.66L518.6 264.7C514.7 278.5 502.1 288 487.8 288H170.7L179.9 336H488C501.3 336 512 346.7 512 360C512 373.3 501.3 384 488 384H159.1C148.5 384 138.6 375.8 136.4 364.5L76.14 48H24C10.75 48 0 37.25 0 24C0 10.75 10.75 0 24 0H96zM128 464C128 437.5 149.5 416 176 416C202.5 416 224 437.5 224 464C224 490.5 202.5 512 176 512C149.5 512 128 490.5 128 464zM512 464C512 490.5 490.5 512 464 512C437.5 512 416 490.5 416 464C416 437.5 437.5 416 464 416C490.5 416 512 437.5 512 464z" />
                                    </svg>
                                    <div className="rounded-circle bg-danger d-flex justify-content-center align-item-center"
                                        style={{
                                            color: "white",
                                            width: "1.5rem",
                                            height: "1.5rem",
                                            position: "absolute",
                                            bottom: 0,
                                            right: 0,
                                            transform: "translate(25%, 25%)",
                                        }}
                                    >
                                        {sessionStorage.getItem('productsQuantityCart')}
                                    </div>
                            </Button>

                        </nav>
                    </div>

                    <div>
                        <label>
                             Nom du produit :  <input  type="text" value={nameFilter} onChange={(e) => setNameFilter(e.target.value)} />
                        </label>
                        <label>
                             Inventaire :
                                <select value={inventoryFilter} onChange={(e) => setInventoryFilter(e.target.value)} >
                                    <option value="INSTOCK">INSTOCK</option>
                                    <option value="LOWSTOCK">LOWSTOCK</option>
                                    <option value="OUTOFSTOCK">OUTOFSTOCK</option>
                                </select>
                        </label>
                        <label>
                        Taille de la page :
                                <input type="number" value={pageSize} onChange={(e) => setPageSize(Number(e.target.value))} min="1" />
                        </label>
                    </div>


                    <div className="card-body">
                        <div className="divbtn">
                            <Link to="/create/product" className="btn btn-success">Add New product</Link>
                        </div>
                        <table className="table table-bordered">
                            <thead className="bg-dark text-white">
                                <tr>
                                    <td>Id product</td>
                                    <td>Code product</td>
                                    <td>Name product</td>
                                    <td>Price product</td>
                                    <td>Quantity product</td>
                                    <td>Action</td>
                                </tr>
                            </thead>

                            <tbody>

                                {products &&
                                    products.content.map(product => (
                                        <tr key={product.idProduct}>
                                            <td>{product.idProduct}</td>
                                            <td>{product.codeProduct}</td>
                                            <td>{product.nameProduct}</td>
                                            <td>{product.priceProduct.toFixed(2)}</td>
                                            <td>{product.quantityProduct}</td>
                                            <td>
                                                <button className="btn btn-primary"  onClick={() => handleAddToCart(product)} >Add to cart</button>
                                                   
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>

                        </table>

                        <div className="card-footer">
                                    <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0}>
                                            Précédent
                                    </button>
                                    <span>
                                            Page {currentPage + 1} sur {totalPages}
                                    </span>
                                    <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages - 1}>
                                            Suivant
                                    </button>
                        </div>

                 </div>   
                </div>
            </div>
            <Cart show={showCart} onHide={() => setShowCart(false)} />
            <ProductModal
                show={showModal}
                onHide={() => setShowModal(false)}
                selectedProduct={selectedProduct}
                quantity={quantity}
                setQuantity={setQuantity}
                onConfirm={handleAddToCartConfirm}
            />
        </div>
    )
}

export default ListProduct
