import { useState, useEffect } from 'react';
import PostFeedbackTagButton from './PostFeedbackTagButton.js';
import Errors from '../../Errors.js';

function PostFeedbackTagForm({ object, user, handleTagClick }) {
  const [feedbackTags, setFeedbackTags] = useState([]);
  const [update, setUpdate] = useState([]);
  const [errors, setErrors] = useState([]);

  const isPost = (object.postId ? true : false);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/feedback-tag`);
        const data = await response.json();
        setFeedbackTags(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [update]);

  const handleButtonUpdate = () => {
    if (update) {
      setUpdate(false);
    } else {
      setUpdate(true);
    }
    handleTagClick();
  }

  const makeFeedbackTag = (feedbackTag) => {
    return (
      <PostFeedbackTagButton key={feedbackTag.feedbackTagId} feedbackTag={feedbackTag} object={object} user={user} setErrors={setErrors} handleButtonUpdate={handleButtonUpdate} />
    );
  };

  return (
    <> 
      <Errors errors={errors} />
      <div>
        {feedbackTags.map(feedbackTag => makeFeedbackTag(feedbackTag))}
      </div>
    </>
  );

} export default PostFeedbackTagForm;