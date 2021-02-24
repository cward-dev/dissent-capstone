import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../../Errors.js';

function AddFeedbackTag ( { user } ) {

  const [addFeedbackTag, setAddFeedbackTag] = useState(false);

  const [feedbackTag, setFeedbackTag] = useState( {
    "name": "",
    "colorHex": "",
  } );

  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (event) => {
    const updatedFeedbackTag = {...feedbackTag};
    updatedFeedbackTag[event.target.name] = event.target.value;
    setFeedbackTag(updatedFeedbackTag);
  };

  const handleAddSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(feedbackTag)
    };

    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/feedback-tag`, init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.feedbackTagId) {
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
    setAddFeedbackTag(true);
  }

  const handleCancel = () => {
    setAddFeedbackTag(false);
  }

  return (
    <form onSubmit={handleAddSubmit}>
      <Errors errors={errors} />
      {addFeedbackTag ?
        <div className="form-row mb-4 mx-1">
          <div className="col-9">
            <label htmlFor="name" className="pl-2 pt-2">New Feedback Tag</label>
          </div>
          <input type="text" className="form-control mb-3 mx-2" id="name" name="name" onChange={handleChange} />
          <label htmlFor="colorHex" className="pl-2 pt-2">Color</label>
          <input type="color" id="colorHex" name ="colorHex" className="col-2 form-control ml-2 p-1" required onChange={handleChange}></input>
          <div className="col text-right mr-2">
            <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
            <button type="submit" className="btn btn-dark mx-2 btn-sm">Submit</button>
          </div>
        </div>
      : <div className="d-flex flex-row justify-content-center"><button className="btn btn-secondary btn-sm col" onClick={handleAddClick}>Add Feedback Tag</button></div>}
    </form>
  );
}

export default AddFeedbackTag;