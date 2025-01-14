import React, { useState } from 'react'
import { Link, useNavigate } from "react-router-dom";

const CreateProduct = () => {

    const[codeProduct,codeProductChange]=useState("");
    const[nameProduct,nameProductChange]=useState("");
    const[descriptionProduct,descriptionProductChange]=useState("");
    const[imageProduct,imageProductChange]=useState("");
    const[categoryProduct,categoryProductChange]=useState("");
    const[priceProduct,priceProductChange]=useState(0);
    const[quantityProduct,quantityProductChange]=useState(0);
    const[internalReferenceProduct,internalReferenceProductchange]=useState("");
    const[shellIdProduct,shellIdProductChange]=useState(0);
    const[inventoryStatus,inventoryStatusChange]=useState("");
    const[ratingProduct,ratingProductChange]=useState(0);


    const navigate=useNavigate();

    const handlesubmit=(e)=>{
        let token = sessionStorage.getItem('token');
        console.log("token : "+token);
        e.preventDefault();
        const product = {codeProduct,nameProduct,descriptionProduct,imageProduct,
            categoryProduct,priceProduct,quantityProduct,internalReferenceProduct,shellIdProduct,inventoryStatus,ratingProduct};
        console.log("product :" + {codeProduct,nameProduct,descriptionProduct,imageProduct,
            categoryProduct,priceProduct,quantityProduct,internalReferenceProduct,shellIdProduct,inventoryStatus,ratingProduct});


        fetch("http://localhost:9999/api/v1/products",{
            method:"POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "content-type": "application/json"
            },
            body:JSON.stringify(product)
          }).then((res)=>{
            console.log("res : "+res.text())
            alert('Saved successfully.')
            navigate('/products');
          }).catch((err)=>{
            console.log(err.message)
          })

    }





    return (
        <div>

            <div className="row">
                <div className="offset-lg-3 col-lg-6">
                    <form className="container" onSubmit={handlesubmit}>
                        <div className="card" style={{"textAlign":"left"}}>
                            <div className="card-title">
                                <h2>Create Product</h2>
                            </div>
                            <div className="card-body">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Code Product</label>
                                            <input required value={codeProduct}  onChange={e=>codeProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>

                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Name Product</label>
                                            <input value={nameProduct} onChange={e=>nameProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>

                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Description Product</label>
                                            <input value={descriptionProduct} onChange={e=>descriptionProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Image Product</label>
                                            <input value={imageProduct} onChange={e=>imageProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Category Product</label>
                                            <input value={categoryProduct} onChange={e=>categoryProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Price Product</label>
                                            <input value={priceProduct} onChange={e=>priceProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Quantity Product</label>
                                            <input value={quantityProduct} onChange={e=>quantityProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Internal Reference Product</label>
                                            <input value={internalReferenceProduct} onChange={e=>internalReferenceProductchange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>ShellId Product</label>
                                            <input value={shellIdProduct} onChange={e=>shellIdProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Inventory Status</label>
                                            <select value={inventoryStatus} onChange={(e) => inventoryStatusChange(e.target.value)} className="form-control">
                                                <option value="INSTOCK">INSTOCK</option>
                                                <option value="LOWSTOCK">LOWSTOCK</option>
                                                <option value="OUTOFSTOCK">OUTOFSTOCK</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Rating Product</label>
                                            <input value={ratingProduct} onChange={e=>ratingProductChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                           <button className="btn btn-success" type="submit">Save</button>
                                           <Link to="/" className="btn btn-danger">Back</Link>
                                        </div>
                                    </div>

                                </div>

                            </div>

                        </div>

                    </form>

                </div>
            </div>
        </div>
    );
}

export default CreateProduct
