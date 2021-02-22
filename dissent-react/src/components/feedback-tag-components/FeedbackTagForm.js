import { useState, useEffect } from 'react';
import FeedbackTagIcon from './FeedbackTagIcon.js';
import Article from '../article-components/ArticleCard';
import { PieChart } from 'react-minimal-pie-chart';
import Errors from '../Errors.js';

// JSON
const DEFAULT_TAG = {
  "articleId": "",
  "userId": "",
  "feedbackTag": {
    "feedbackTagId": {
      "count": 0,
      "colorHex": "#000000"
    }
  }
};

const DEF_FB_TAG = {
  "feedbackTagId": 0,
}

function FeedbackTagForm({ object, user }) {
  
  const [tag, setTag] = useState(DEFAULT_TAG);
  const [errors, setErrors] = useState([]);
  const [feedbackTagJSON, setFeedbackTagJSON] = useState(DEF_FB_TAG);
  const { feedbackTagId, name } = feedbackTagJSON;

  const [userFeedBackTagsForArticle, setUserFeedbackTagsForArticle] = useState([]);


 // grabs all user feedback for article
 
 useEffect(()=> {

  fetch(`http://localhost:8080/api/article/feedback-tag/${object.articleId}/${user.userId}`)
  .then (response => {
    if(response.status!= 200){
      return Promise.reject("feedbacktag fetch failed");
    }
    return response.json();
  })
  .then(json => setUserFeedbackTagsForArticle(json))
  .catch(console.log);
 },[]);



  const addFeedbackTag = async (tag) => {
    console.log(tag);

    const addedTag = {
      "articleId": tag.articleId,
      "userId": tag.userId,
      "feedbackTag": tag.feedbackTag
    }


    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(addedTag)
    };

    try {

      const response = await fetch("http://localhost:8080/api/article/feedback-tag", init);
      console.log(response);
      if (response.status === 201 || response.status === 400) {
        // maybe fetch data go back to article fetch and get data...?
        setErrors([]);
      } else if (response.status === 500) {
        throw new Error(["Duplicate Entries are not allowed"])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      console.log(error);
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else if (error.message === "Duplicate Entries are not allowed") {
        setErrors(["Duplicate Entries are not allowed"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  }

  const deleteTag = async (tag) => {
    console.log(tag);

    fetch(`http://localhost:8080/api/article/feedback-tag/${tag.articleId}/${user.userId}/${feedbackTagId}`, {
      method: "DELETE"
    })
      .then(response => {
        if (response.status === 204) {
          setErrors([]);
        } else if (response.status === 404) {
          Promise.reject('Tag for article not found.');
        } else {
          Promise.reject('Shoot! Something unexpected went wrong :(');
        }
      })
      .catch(error => console.log(error));
  }




  const handleSubmit = (event) => {
    event.preventDefault();
    tag.articleId = object.articleId;
    tag.userId = user.userId;
    tag.feedbackTag = feedbackTagJSON;
  
      addFeedbackTag(tag);
    
    console.log(tag);
  };

  const onChangeHandler = (event) => {
    event.preventDefault();
    const newFeedbackTagJSON = { ...feedbackTagJSON };
    newFeedbackTagJSON[event.target.name] = event.target.value;
    console(newFeedbackTagJSON);
    setFeedbackTagJSON(newFeedbackTagJSON);
    console.log(feedbackTagJSON);

  };



  const [hasSelected, setSelected] = useState(false);
  const [isToggledSound, setToogleSound] = useState(false);
  const [isToggledFallacious, setToogleFallacious] = useState(false);
  const [isToggledBiased, setToogleBiased] = useState(false);

  const toggleTrueFalseSound = (event) => {
    event.preventDefault();
    setToogleSound(!isToggledSound);

    event.preventDefault();
    setFeedbackTagJSON(1);
    const newFeedbackTagJSON = { ...feedbackTagJSON };
    setFeedbackTagJSON(newFeedbackTagJSON);
    console.log(feedbackTagJSON);

    if(userFeedBackTagsForArticle === null){
      tag.articleId = object.articleId;
      tag.userId = user.userId;
      tag.feedbackTag = feedbackTagJSON;
  
      addFeedbackTag(tag);
      setSelected(true);

      //disable the rest of the buttons
      //set style
    } else {
      const tagToDelete = userFeedBackTagsForArticle[0];
      deleteTag(tag);
      setSelected(false);
    }
  }


  const toggleTrueFalseFallacious = () => setToogleFallacious(!isToggledFallacious);
  const toggleTrueFalseBiased = () => setToogleBiased(!isToggledBiased);



  return (
    <div className="container alert alert-dark">
      {/* <div className="row ">
    
        <Errors errors={errors} />
        <form onSubmit={handleSubmit}>
          <label htmlFor="feedbackTagId"><h3>Feedback</h3></label>
          <select id="feedbackTagId" name="feedbackTagId"
            onChange={onChangeHandler} value={feedbackTagId}>
            <option value="">--Give Feedback--</option>
            <option value="1">Sound</option>
            <option value="2">Fallacious</option>
            <option value="3">Biased</option>
          </select>
          <button type="submit">Submit</button>
        </form>

        <div> */}

        <div onChange={onChangeHandler}>
        <button onClick={toggleTrueFalseSound}>Sound </button>
        <button onClick={toggleTrueFalseFallacious}>Fallacious</button>
        <button onClick={toggleTrueFalseBiased}>Biased</button>

        </div>

    </div>
  );

} export default FeedbackTagForm;