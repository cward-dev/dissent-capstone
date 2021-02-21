import { useState , useEffect } from 'react';
import FeedbackTagIcon from './FeedbackTagIcon.js';
import AddFeedbackTag from './AddFeedbackTag.js';
import Article from '../article-components/ArticleCard';
import { PieChart } from 'react-minimal-pie-chart';

// JSON
const DEFAULT_TAG = {
  "id": "",
  "userId": "",
  "feedbackTag": {
    "feedbackTagId": {
      "count": 0,
      "colorHex": "#000000"
    }
  }
};

const DEF_FB_TAG ={
  "feedbackTagId": 0,
  "name": "",
  "colorHex": "",
  "active": false
}

function FeedbackTagForm ( { object, user } ) {
  const [tag, setTag] = useState(DEFAULT_TAG);
  const [errors, setErrors] = useState([]);
  const [feedbackTagJSON, setFeedbackTagJSON] = useState(DEF_FB_TAG);
  const { feedbackTagId, name } = feedbackTagJSON;

  
  let objectId = '';

  if (object.articleId) {
    objectId = object.articleId;
  } else if (object.postId) {
    objectId = object.postId;
  }

  const addFeedbackTag = async (tag) => {
    const addedTag = {
      "id": tag.id,
      "userId": user.userId,
      "feedbackTag": tag.feedbackTag
    };

    const init = {
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(addedTag)
    };

    try {
      const response = await fetch("http://localhost:8080/api/feedback-tag", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    tag.id = objectId;
    tag.userId ='103a7d9b-f72b-4469-b1a3-bdba2f6356b4';

    tag.feedbackTag = feedbackTagJSON;
    addFeedbackTag(tag);
    console.log(tag);
  };

  const onChangeHandler = (event) =>{
    event.preventDefault();

    console.log(event.target.name);
    console.log(event.target.value);

    const newFeedbackTagJSON= {...feedbackTagJSON};
    newFeedbackTagJSON[event.target.name] = event.target.value;
    console.log(newFeedbackTagJSON.feedbackTagId);
    setFeedbackTagJSON(newFeedbackTagJSON);
  };

  return (
    <div className="container alert alert-dark">
      <div className="row ">
        <div className="col-4">
          <form onSubmit={handleSubmit}>
            <label htmlFor="feedbackTagId"><h3>Feedback</h3></label>
            <select id="feedbackTagId" name="feedbackTagId" 
            onChange={onChangeHandler} value = {feedbackTagId}>
              <option value="">--Give Feedback--</option>
              <option value="1">Sound</option>
              <option value="2">Fallacious</option>
              <option value="3">Biased</option>
            </select>
            <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    </div>
  );

} export default FeedbackTagForm;