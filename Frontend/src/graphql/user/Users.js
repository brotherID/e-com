import React, { useState } from "react";
import { gql, useQuery } from "@apollo/client";
import UserModal from "./UserModal";

export const GET_USERS = gql`
  query {
    users {
      username
      firstname
      email
    }
  }
`;

const Users = () => {
  const { loading, error, data } = useQuery(GET_USERS);
  const [showUserModal, setShowUserModal] = useState(false);

  if (loading) return <p>Chargement...</p>;
  if (error) return <p>Erreur : {error.message}</p>;

  const handleUpdateUser = user => {
    console.log("user :" + user.email);
  };

  const handleAddNewUser = () => {
    setShowUserModal(true);
  };

  return (
    <div>
      <div className="container">
        <div className="card">
          <div className="card-title">
            <h2>User Listing with graphql</h2>
          </div>
          <div className="card-body">
            <div className="divbtn">
              <button
                type="button"
                className="btn btn-success"
                onClick={() => handleAddNewUser()}
              >
                Add New User
              </button>
            </div>
            <table className="table table-bordered">
              <thead className="bg-dark text-white">
                <tr>
                  <td>User name</td>
                  <td>First name</td>
                  <td>Email</td>
                  <td>Action</td>
                </tr>
              </thead>

              <tbody>
                {data &&
                  data.users.map(user => (
                    <tr key={user.email}>
                      <td>{user.username}</td>
                      <td>{user.firstname}</td>
                      <td>{user.email}</td>
                      <td>
                        <button
                          type="button"
                          className="btn btn-success"
                          onClick={() => handleUpdateUser(user)}
                        >
                          Update User
                        </button>
                      </td>
                    </tr>
                  ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <UserModal show={showUserModal} onHide={() => setShowUserModal(false)} />
    </div>
  );
};

export default Users;
