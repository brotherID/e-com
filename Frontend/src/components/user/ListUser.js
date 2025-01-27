import { useEffect, useState , useContext } from "react";
import axios from "axios";
import {Context} from "../../App";

const ListUser = () => {
    const [loggedIn, setLoggedIn] = useContext(Context);
    const [users , userdatachange] = useState(null);
     

    /* useEffect(() => {
        fetch("http://localhost:9999/api/v1/users")
        .then((res) => {
            return res.json();
        }).then((resp) => {
            userdatachange(resp);
            console.log("resp ", resp);
        }).catch((err) => {
            console.log(err.message);
        })
    }, []) */

 

    useEffect(() => {
            axios.get("http://localhost:9999/api/v1/users")
            .then((resp) => {
                userdatachange(resp.data);
                console.log("resp ", resp);
                console.log("resp data ", resp.data);
                console.log("loggedIn " + loggedIn);
            }).catch((err) => {
                console.log(err.message);
            })
    }, [])


    return (
        <div className="container">
        <div className="card">
            <div className="card-title">
                <h2>User Listing</h2>
            </div>
            <div className="card-body">
                <table className="table table-bordered">
                    <thead className="bg-dark text-white">
                        <tr>
                            <td>User name</td>
                            <td>First name</td>
                            <td>Email</td>
                        </tr>
                    </thead>

                    <tbody>
                        {users &&
                            users.map(user => (
                                <tr key={user.email}>
                                    <td>{user.username}</td>
                                    <td>{user.firstname}</td>
                                    <td>{user.email}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    );
}

export default ListUser
