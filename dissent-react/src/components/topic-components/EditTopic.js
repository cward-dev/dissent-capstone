import { useState } from 'react';
import Errors from '../Errors.js';

function EditTopic ( { topic, setTopicToEdit, user } ) {

  const [editedTopic, setEditedTopic] = useState( {
    "topicName": ''
  } );
  const [errors, setErrors] = useState([]);


  const handleChange = (event) => {
    const updatedTopic = {...topic};
    updatedTopic[event.target.name] = event.target.value;
    setEditedTopic(updatedTopic);
  };

  const handleEditSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(editedTopic)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/topic/${topic.topicId}`, init);

      if (response.status === 204) {
        handleCancel();
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleCancel = () => {
    setTopicToEdit(null);
  }

  return (
    <form className onSubmit={handleEditSubmit}>
      <Errors errors={errors} />
      <div className="form-row mx-1">
        <div className="col">
          <label htmlFor="content" className="pl-2 pt-2">Edit Topic - {topic.topicName}</label>
        </div>
        <input type="text" className="form-control mb-3" id="topicName" name="topicName" defaultValue={topic.topicName} required onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark btn-sm ml-2">Submit</button>
        </div>
      </div>
    </form>
  );
}

export default EditTopic;