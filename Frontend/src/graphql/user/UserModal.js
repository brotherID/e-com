import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { gql, useMutation } from "@apollo/client";
import { toast } from "react-toastify";
import { GET_USERS } from "./Users";

const ADD_USER = gql`
  mutation AddUser(
    $userName: String!
    $firstName: String!
    $email: String!
    $password: String!
  ) {
    addUser(
      userInfoRequest: {
        username: $userName
        firstname: $firstName
        email: $email
        password: $password
      }
    ) {
      username
    }
  }
`;

const UserModal = ({ show, onHide }) => {
  const [userName, setUserName] = useState("");
  const [firstName, setFirstName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [addUser, { data, loading, error }] = useMutation(ADD_USER);

  const initializeInputs = () => {
    setUserName("");
    setFirstName("");
    setEmail("");
    setPassword("");
  };

  const addToastSuccess = () => {
    toast.success("User added successfully! !", {
      position: "top-right",
      autoClose: 3000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true
    });
  };

  const addToastFailed = message => {
    toast.error(message, {
      position: "top-right",
      autoClose: 3000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true
    });
  };

  const handleAddNewUser = async () => {
    console.log("userName : ", userName);
    console.log("firstName : ", firstName);
    console.log("email : ", email);
    console.log("password : ", password);

    if (!userName || !firstName || !email || !password) {
      console.error("Toutes les valeurs doivent être définies !");
      return;
    }
    try {
      const { data } = await addUser({
        variables: {
          userName: userName,
          firstName: firstName,
          email: email,
          password: password
        },
        refetchQueries: [{ query: GET_USERS }]
      });

      if (data && data.addUser) {
        console.log("data :", data.addUser);
        addToastSuccess();
      }
    } catch (error) {
      console.log("error : ", error);
      if (error.graphQLErrors && error.graphQLErrors.length > 0) {
        const errorMessage = error.graphQLErrors[0].message;
        addToastFailed(errorMessage);
      } else {
        addToastFailed("Une erreur inconnue est survenue.");
      }
    } finally {
      onHide();
    }
    initializeInputs();
  };

  return (
    <div>
      <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
          <Modal.Title>User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div>
            <strong>User Name : </strong>
            <input
              required
              type="text"
              value={userName}
              onChange={valeur => setUserName(valeur.target.value)}
            />
          </div>
          <div>
            <strong>First Name : </strong>
            <input
              type="text"
              required
              value={firstName}
              onChange={valeur => setFirstName(valeur.target.value)}
            />
          </div>
          <div>
            <strong>Email : </strong>
            <input
              type="text"
              required
              value={email}
              onChange={valeur => setEmail(valeur.target.value)}
            />
          </div>
          <div>
            <strong>Password : </strong>
            <input
              type="text"
              required
              value={password}
              onChange={valeur => setPassword(valeur.target.value)}
            />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onHide}>
            Cancel
          </Button>
          <Button variant="primary" onClick={() => handleAddNewUser()}>
            Add new User
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default UserModal;
