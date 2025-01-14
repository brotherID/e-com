import React from "react";
import { Modal, Button } from "react-bootstrap";
import { toast } from "react-toastify";

const ProductModal = ({ show, onHide, selectedProduct, quantity, setQuantity, onConfirm }) => {
  
    const handleAddToCart = () => {
        if (!selectedProduct) return;

        if (quantity <= 0 || quantity > selectedProduct.quantityProduct) {
            toast.error("Please select a valid quantity!");
            return;
        }

        const token = sessionStorage.getItem("token");
        fetch(`http://localhost:9999/api/v1/products/${selectedProduct.idProduct}/quantityRequested/${quantity}/cart`, {
            method: "POST",
            headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
            },
        }).then((res)=>{
            console.log("res : "+res)
            console.log("Product added to cart successfully.");
            toast.success(`${selectedProduct.nameProduct} has been added to the cart!`);
            onConfirm(selectedProduct, setQuantity);
            onHide();
            window.location.reload();
        }).catch((err)=>{
            console.log(err.message)
        })
    };


    const handleQuantityChange = (e) => {
        const value = Number(e.target.value);
        if (value > 0 && value <= selectedProduct.quantityProduct) {
          setQuantity(value);
        } else if (value > selectedProduct.quantityProduct) {
          toast.error(`Maximum quantity allowed is ${selectedProduct.quantityProduct}.`);
        } else {
          toast.error("Quantity must be greater than 0.");
        }
      };


  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Product Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {selectedProduct && (
          <>
            <p><strong>Product Name:</strong> {selectedProduct.nameProduct}</p>
            <p><strong>Price:</strong> {selectedProduct.priceProduct}</p>
            <p><strong>Available Quantity:</strong> {selectedProduct.quantityProduct}</p>
            <label>
              Quantity:
              <input
                type="number"
                value={quantity}
                onChange={handleQuantityChange}
                min="1"
                max={selectedProduct.quantityProduct}
              />
            </label>
          </>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Cancel
        </Button>
        <Button variant="primary" onClick={handleAddToCart}>
          Add to Cart
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ProductModal;
