import { useState } from 'react';
import Errors from '../../Errors.js';

function ReactivateTopic ( { topic, setTopicToReactivate, user } ) {

  const [errors, setErrors] = useState([]);

  const handleReactivateSubmit = async (event) => {
    event.preventDefault();

    const topicToReactivate = {
      "topicName": topic.topicName
    }

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(topicToReactivate)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/topic`, init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.topicId) {
          handleCancel();
          setErrors([]);
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    setTopicToReactivate(null);
  }

  return (
    <form onSubmit={handleReactivateSubmit}>
      <Errors errors={errors} />
      <div className="d-flex flex-row justify-content-between align-items-center alert alert-dark">
        <div>Are you sure?</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-info btn-sm ml-2">Reactivate {topic.topicName}</button>
        </div>
      </div>
    </form>
  );
}

export default ReactivateTopic;