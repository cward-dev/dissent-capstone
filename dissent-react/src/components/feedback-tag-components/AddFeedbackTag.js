import { useState , useEffect } from 'react';
import AddReplyPost from "../post-components/AddReplyPost";

function AddFeedbackTag ( { object, userId, feedbackTags, setFeedbackTags, setErrors } ) {

  const addFeedbackTag = () => {

    let objectFeedbackTag = null;

    if (object.articleId) {
      objectFeedbackTag = {
        "articleId": object.articleId,
        "userId": userId,
        "feedbackTag": object.feedbackTag
      };
    } else if (object.postId) {
      objectFeedbackTag = {
        "postId": object.postId,
        "userId": userId,
        "feedbackTag": object.feedbackTag
      };
    }

    const init = {
      method : "POST",
      headers: {
        "Content-Type" : "application/json",
        "Accept" : "application/json"
      },
      body : JSON.stringify(objectFeedbackTag)
    }

    fetch("http://localhost:8080/api/article/feedback-tag", init)
    .then(response => {
      if(response.status===201 || response.status === 400){
        return response.json();
      } else {
        Promise.reject("Something went wrong")
      }
    })
    .then(data => {
      if(data.feedbackTagId){
        setFeedbackTags([...feedbackTags, data])
        setErrors([]);
        } else{
          setErrors(data);
        }
      })
    .catch(error => console.log(error));
  }
}

export default AddFeedbackTag;