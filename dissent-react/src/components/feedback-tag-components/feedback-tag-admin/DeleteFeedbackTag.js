import { useState } from 'react';
import Errors from '../../Errors.js';

function DeleteFeedbackTag ( { feedbackTag, setFeedbackTagToDelete, update, setUpdate, user } ) {

  const [errors, setErrors] = useState([]);

  const handleDeleteSubmit = async (event) => {
    try {
      const response = await fetch(`http://localhost:8080/api/feedback-tag/${feedbackTag.feedbackTagId}`, { method: "DELETE" } );

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
    setFeedbackTagToDelete(null);
  }

  return (
    <form onSubmit={handleDeleteSubmit}>
      <Errors errors={errors} />
      <div className="d-flex flex-row justify-content-between align-items-center alert alert-dark">
        <div>Are you sure?</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2" onClick={handleDeleteSubmit}>Inactivate {feedbackTag.name}</button>
        </div>
      </div>
    </form>
  );
}

export default DeleteFeedbackTag;