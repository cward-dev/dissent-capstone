import { useState, useEffect } from 'react';
import ArticleFeedbackTagButton from './ArticleFeedbackTagButton.js';
import Errors from '../../Errors.js';

function ArticleFeedbackTagForm ( { object, user, handleTagClick, update, setUpdate } ) {
  const [feedbackTags, setFeedbackTags] = useState([]);
  const [errors, setErrors] = useState([]);

 

  constgetData = async () => {​​​​​​​​
  try {​​​​​​​​
  constresponse = awaitfetch(`${process.env.REACT_APP_API_URL}/api/feedback-tag`);
  constdata = awaitresponse.json();
  setFeedbackTags(data);
  handleTagClick();
      }​​​​​​​​ catch (error) {​​​​​​​​
  setErrors(["Something went wrong with our database, sorry!"]);
      }​​​​​​​​
    }​​​​​​​​;
   
  useEffect(() => {​​​​​​​​
  getData();
    }​​​​​​​​, []);
  
  
  const handleButtonUpdate = () => {
    getData();
    handleTagClick();
  }

  const makeFeedbackTag = (feedbackTag) => {
    return (
      <ArticleFeedbackTagButton key={feedbackTag.feedbackTagId} feedbackTag={feedbackTag} object={object} user={user} setErrors={setErrors} handleButtonUpdate={handleButtonUpdate} update={update} setUpdate={setUpdate} />
    );
  };

  return (
    <> 
      <Errors errors={errors} />
      <div className="btn-group-vertical mr-3">
        {feedbackTags.map(feedbackTag => makeFeedbackTag(feedbackTag))}
      </div>
    </>
  );

} export default ArticleFeedbackTagForm;