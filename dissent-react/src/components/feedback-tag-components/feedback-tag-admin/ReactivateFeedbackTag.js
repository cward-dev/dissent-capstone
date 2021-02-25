import { useState } from 'react';
import Errors from '../../Errors.js';

function ReactivateFeedbackTag ( { feedbackTag, setFeedbackTagToReactivate, update, setUpdate, user } ) {

  const [errors, setErrors] = useState([]);

  const handleReactivateSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/feedback-tag/activate/${feedbackTag.feedbackTagId}`, { method: "PUT" } );

      if (response.status === 204) {
        
        if (update === true) {
          setUpdate(false)
        } else {
          setUpdate(true);
        }
        
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