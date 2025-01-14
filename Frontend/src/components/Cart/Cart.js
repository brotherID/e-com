import React, { useState, useEffect } from 'react'
import { Offcanvas , Form, Button } from 'react-bootstrap'
import { BsBag } from "react-icons/bs"
import './Cart.css';
import { toast } from "react-toastify";


const Cart = ({ show, onHide }) => {
    
    const [cartProducts, setCartProducts] = useState({ shoppedProducts: [] });
    const [promoCode, setPromoCode] = useState('');
    const [discountMessage, setDiscountMessage] = useState('');

    useEffect(() => {
        setDiscountMessage('');
        if (show) {
          const token = sessionStorage.getItem("token");
          fetch("http://localhost:9999/api/v1/products/cart-user", {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
              "content-type": "application/json",
            },
          })
            .then((res) => res.json())
            .then(
                (data) => {
                 setCartProducts(data);
                 console.log("data  productResponses ", data.shoppedProducts);
                 console.log("data  totalAmount ", data.totalAmount);
                 }
            )
            .catch((err) => {
              console.error("Failed to fetch cart data:", err);
            });
        }
    }, [show]);


    const applyPromoCode = () => {
        setDiscountMessage('');
        setPromoCode('');
        const token = sessionStorage.getItem("token");
        fetch(`http://localhost:9999/api/v1/promotions/discount/${promoCode}`, {
            method: "GET",
            headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
            },
        })
        .then((res)=>{    
            console.log("res.status : "+res.status);
            if (res.status === 200) {
                toast.success(`${promoCode} Promo code applied successfully!`);    
                setDiscountMessage('Promo code applied successfully!');
                return res.json();  
            } else {
                setDiscountMessage('Invalid promo code. Please try again.');
            } 
            console.log("res : "+res);
            
        }).catch((err)=>{
            console.log(err.message)
        })
    };




    return (
         <Offcanvas show={show} onHide={onHide} style={{ width: "500px", maxWidth: "90%", margin: "0 auto" }}>
             <Offcanvas.Header closeButton>
                 <Offcanvas.Title>My Cart  ({cartProducts.shoppedProducts.length || 0}) </Offcanvas.Title>
             </Offcanvas.Header>
             <Offcanvas.Body>
                   
             {cartProducts && cartProducts.shoppedProducts.length > 0 ? (
                <>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Name Product</th>
                            <th>Price Product</th>
                            <th>Code Product</th>
                            <th>Quantity</th>
                        </tr>
                        </thead>
                        <tbody>
                            {cartProducts.shoppedProducts.map((product) => (
                            <tr key={product.idProduct}>
                                <td>{product.nameProduct}</td>
                                <td>{product.priceProduct.toFixed(2)} DH</td>
                                <td>{product.codeProduct}</td>
                                <td>{product.quantityRequested}</td>
                            </tr>
                            ))}
                        </tbody>
                    </table>
                    <div className="text-end mt-3">
                    <strong>Total Amount: {cartProducts.totalAmount.toFixed(2)} DH</strong>
                    </div>

                    <div className="mt-3">
                        <Form.Group controlId="promoCode">
                            <Form.Label>Apply Promo Code</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter promo code"
                                value={promoCode}
                                onChange={(e) => setPromoCode(e.target.value)}
                            />
                        </Form.Group>
                        <Button variant="primary" className="mt-2" onClick={applyPromoCode}>
                            Apply
                        </Button>
                        <p className="mt-2">{discountMessage}</p>
                    </div>
                    


                </>
                ) : (
                        <div className="empty-cart-container">
                            <BsBag size={50} className="text-secondary"  /> 
                            <p className="mt-3">Your cart is empty.</p>
                        </div>
                )}

             </Offcanvas.Body>
         </Offcanvas>
    )
}

export default Cart
