import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function DeleteTopic ( { topic, handleDeleteClick, handleTopicsUpdate, user } ) {

  const [editedTopic, setEditedTopic] = useState( {
    "topicName": ''
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedTopic = {...topic};
    updatedTopic[event.target.name] = event.target.value;
    setEditedTopic(updatedTopic);
  };

  const handleDeleteSubmit = async (event) => {
    try {
      const response = await fetch(`http://localhost:8080/api/topic/${topic.topicId}`, { method: "DELETE" } );

      if (response.status === 204) {
        history.push(``)
        handleTopicsUpdate();
        handleCancel();
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    handleDeleteClick();
  }

  return (
    <form onSubmit={handleDeleteSubmit}>
      <hr></hr>
      <Errors errors={errors} />
      <div className="form-row alert alert-dark">
        <div className="mb-3">Are you sure? Deletions are permanent.</div>
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-danger btn-sm ml-2">Delete</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default DeleteTopic;