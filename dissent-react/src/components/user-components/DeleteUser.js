import { useState } from 'react';
import Errors from '../Errors.js';

function DeleteUser ( { user, setUserToDelete, update, setUpdate } ) {

  const [errors, setErrors] = useState([]);

  const handleDeleteSubmit = async (event) => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/${user.userId}`, { method: "DELETE" } );

      if (response.status === 204) {

        if (update === true) {
          setUpdate(false)
        } else {
          setUpdate(true);
        }

        handleCancel();
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    setUserToDelete(null);
  }

  return (
    <form onSubmit={handleDeleteSubmit}>
      <Errors errors={errors} />
      <div className="d-flex flex-row justify-content-between align-items-center alert alert-dark">
        <div>Are you sure?</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2" onClick={handleDeleteSubmit}>Delete {user.username}</button>
        </div>
      </div>
    </form>
  );
}

export default DeleteUser;