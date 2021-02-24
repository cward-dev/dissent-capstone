import { useState, useEffect } from 'react';
import PostFeedbackTagButton from './PostFeedbackTagButton.js';
import Errors from '../../Errors.js';

function PostFeedbackTagForm ( { object, user, handleTagClick, update, setUpdate } ) {
  const [feedbackTags, setFeedbackTags] = useState([]);
  const [errors, setErrors] = useState([]);

  const getData = async () => {
    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/feedback-tag`);
      const data = await response.json();
      setFeedbackTags(data);
      handleTagClick();
    } catch (error) {
      setErrors(["Something went wrong with our database, sorry!"]);
    }
  };
 
  useEffect(() => {
    getData();
  }, []);

  const handleButtonUpdate = () => {
    getData();
  }

  const makeFeedbackTag = (feedbackTag) => {
    return (
      <PostFeedbackTagButton key={feedbackTag.feedbackTagId} feedbackTag={feedbackTag} object={object} user={user} setErrors={setErrors} handleButtonUpdate={handleButtonUpdate} update={update} setUpdate={setUpdate} />
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

} export default PostFeedbackTagForm;