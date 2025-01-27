import React, { useState , useContext} from 'react'
import { useNavigate } from 'react-router';
import { Context } from "../../App";

const LoginPage = () => {
    const [loggedIn, setLoggedIn] = useContext(Context);
    const [email,emailChange] = useState("");
    const [password,passwordChange] = useState("");


    const navigate=useNavigate();


    const handlesubmit=(e)=>{
        e.preventDefault();
        const credentiel = {email,password};
        console.log(credentiel);

       fetch("http://localhost:9999/api/authentication/token",
            {
                method:"POST",
                headers:{"content-type":"application/json"},
                body:JSON.stringify(credentiel)
            })
            .then((data) => {
                return data.json();
            })
            .then(
                (res)=>{
                    console.log("*** token : "+res.token); 
                    setLoggedIn(true);
                    console.log("loggedIn : "+loggedIn);
                    sessionStorage.setItem('token',res.token);
                    navigate('/products');
                }
            )
            .catch((err)=>{
                console.log(err.message)
            })
    }



    return (
        <div className="row">
            <div className="offset-lg-3 col-lg-6">
                <form className="container" onSubmit={handlesubmit} >
                    <div className="card" style={{"textAlign":"left"}}>
                        <div className="card-title">
                            <h2>Sign in</h2>
                        </div>
                        <div className="card-body">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="form-group">
                                        <label>Email</label>
                                        <input required  value={email} onChange={e=>emailChange(e.target.value)}  className="form-control"></input>
                                    </div>
                                </div>
                                <div className="col-lg-12">
                                    <div className="form-group">
                                        <label>Password</label>
                                        <input required type="password"  value={password} onChange={e=>passwordChange(e.target.value)} className="form-control"></input>
                                    </div>
                                </div>
                                <div className="col-lg-12">
                                    <div className="form-group">
                                       <button className="btn btn-success" type="submit">Sign in</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginPage
