import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../../Errors.js';

function AddTopic ( { user } ) {

  const [addTopic, setAddTopic] = useState(false);

  const [topic, setTopic] = useState( {
    "topicName": ""
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedTopic = {...topic};
    updatedTopic[event.target.name] = event.target.value;
    setTopic(updatedTopic);
  };

  const handleAddSubmit = async (event) => { // TODO WHY AREN'T COMPONENTS UPDATING WITH ROUTE?
    event.preventDefault();

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(topic)
    };

    try {
      const response = await fetch("http://localhost:8080/api/topic", init);

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

  const handleAddClick = () => {
    setAddTopic(true);
  }

  const handleCancel = () => {
    setAddTopic(false);
  }

  return (
    <form onSubmit={handleAddSubmit}>
      <Errors errors={errors} />
      {addTopic ?
        <div className="form-row mb-4 mx-1">
          <div className="col-9">
            <label htmlFor="content" className="pl-2 pt-2">New Topic</label>
          </div>
          <input type="text" className="form-control mb-3 mx-2" id="topicName" name="topicName" onChange={handleChange} />
          <div className="col text-right mr-2">
            <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
            <button type="submit" className="btn btn-dark btn-sm mx-2">Submit</button>
          </div>
        </div>
      : <div className="d-flex flex-row justify-content-center"><button className="btn btn-secondary btn-sm col" onClick={handleAddClick}>Add Topic</button></div>}
    </form>
  );
}

export default AddTopic;