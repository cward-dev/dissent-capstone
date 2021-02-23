import { useState } from 'react';
import Errors from '../../Errors.js';

function ReactivateFeedbackTag ( { feedbackTag, setFeedbackTagToReactivate, user } ) {

  const [errors, setErrors] = useState([]);

  const handleReactivateSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/feedback-tag/activate/${feedbackTag.feedbackTagId}`, { method: "PUT" } );

      if (response.status === 204) {
        handleCancel();
        setErrors([]);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    setFeedbackTagToReactivate(null);
  }

  return (
    <form onSubmit={handleReactivateSubmit}>
      <Errors errors={errors} />
      <div className="d-flex flex-row justify-content-between align-items-center alert alert-dark">
        <div>Are you sure?</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-info btn-sm ml-2">Reactivate {feedbackTag.name}</button>
        </div>
      </div>
    </form>
  );
}

export default ReactivateFeedbackTag;