import { useState } from 'react';
import Errors from '../../Errors.js';

function EditTopic ( { topic, setTopicToEdit, update, setUpdate, user } ) {

  const [editedTopic, setEditedTopic] = useState( {
    "topicId": topic.topicId,
    "topicName": topic.topicName
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

        if (update === true) {
          setUpdate(false);
        } else {
          setUpdate(true);
        }

        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
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
    <form onSubmit={handleEditSubmit}>
      <Errors errors={errors} />
      <div className="form-row mx-1">
        <div className="col">
          <label htmlFor="topicName" className="pl-2 pt-2">Edit Topic - {topic.topicName}</label>
        </div>
        <input type="text" className="form-control mb-3 mx-2" id="topicName" name="topicName" value={topic.topicName} required onChange={handleChange} />
        <div className="col text-right mr-2">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark btn-sm mx-2">Submit</button>
        </div>
      </div>
    </form>
  );
}

export default EditTopic;