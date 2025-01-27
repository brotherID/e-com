import React, { useState , useContext} from 'react';
import { useNavigate } from "react-router-dom";
import {Context} from "../../App";

const CreateUser = () => {

    const  [loggedIn, setLoggedIn] = useContext(Context);
    const[username,userNameChange]=useState("");
    const[firstname,firstNameChange]=useState("");
    const[email,emailChange]=useState("");
    const[password,passwordChange]=useState("");
    const navigate=useNavigate();

    const handlesubmit= (e)=>{
        e.preventDefault();
        const userdata = {username,firstname,email,password};
        console.log({username,firstname,email,password});
        fetch("http://localhost:9999/api/v1/users/user",{
            method:"POST",
            headers:{"content-type":"application/json"},
            body:JSON.stringify(userdata)
          }).then((res)=>{
            setLoggedIn(false);
            alert('Saved successfully.')
            navigate('/users');
          }).catch((err)=>{
            console.log(err.message)
          })
    }


    return (
        <div>
            <div className="row">
                <div className="offset-lg-3 col-lg-6">
                    <form className="container"  onSubmit={handlesubmit}>

                        <div className="card" style={{"textAlign":"left"}}>
                            <div className="card-title">
                                <h2>Create user</h2>
                            </div>
                            <div className="card-body">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>UserName</label>
                                            <input required  value={username} onChange={e=>userNameChange(e.target.value)}   className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Firstname</label>
                                            <input required value={firstname}  onChange={e=>firstNameChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Email</label>
                                            <input required value={email} onChange={e=>emailChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                            <label>Password</label>
                                            <input  type="password" required  value={password} onChange={e=>passwordChange(e.target.value)} className="form-control"></input>
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="form-group">
                                           <button className="btn btn-success" type="submit">Save</button>
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

export default CreateUser
