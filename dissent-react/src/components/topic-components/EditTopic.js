import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../Errors.js';

function EditTopic ( { topic, handleEditClick, handleTopicsUpdate, user } ) {

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

  const handleEditSubmit = async (event) => { // TODO WHY AREN'T COMPONENTS UPDATING WITH ROUTE?
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
        history.push(`./${editedTopic.topicName}`)
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
    handleEditClick();
  }

  return (
    <form className onSubmit={handleEditSubmit}>
      <Errors errors={errors} />
      <hr></hr>
      <div className="form-row mx-1">
        <div className="col">
          <label htmlFor="content" className="pl-2 pt-2">Edit Topic - {topic.topicName}</label>
        </div>
        <input type="text" className="form-control mb-3" id="topicName" name="topicName" defaultValue={topic.topicName} required onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark ml-2 mr-3 btn-sm">Submit</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default EditTopic;