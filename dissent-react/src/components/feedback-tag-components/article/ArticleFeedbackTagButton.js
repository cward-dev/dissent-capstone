import { useState, useEffect } from 'react';

function ArticleFeedbackTagButton ( { feedbackTag, object, user, setErrors, handleButtonUpdate, update, setUpdate } ) {

  const [tagToggled, setTagToggled] = useState(false);

  useEffect(()=> {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/article/feedback-tag/${object.articleId}/${user.userId}/${feedbackTag.feedbackTagId}`);

        if (response.status === 200) {
          setTagToggled(true);
        } else if (response.status === 204) {
          setTagToggled(false);
        }

        if (update === true) {
          setUpdate(false);
        } else {
          setUpdate(true);
        }
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  const addObjectFeedbackTag = async () => {
    const addedTag = {
      "articleId": object.articleId,
      "userId": user.userId,
      "feedbackTag": {
        "feedbackTagId": feedbackTag.feedbackTagId
      }
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
      
      if (response.status === 201 || response.status === 400) {
        setTagToggled(true);
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

  const deleteObjectFeedbackTag = async () => {
    fetch(`http://localhost:8080/api/article/feedback-tag/${object.articleId}/${user.userId}/${feedbackTag.feedbackTagId}`, {
      method: "DELETE"
    })
      .then(response => {
        if (response.status === 204) {
          setTagToggled(false);
          setErrors([]);
        } else if (response.status === 404) {
          Promise.reject('Tag for article not found.');
        } else {
          Promise.reject('Shoot! Something unexpected went wrong :(');
        }
      })
      .catch(error => console.log(error));
  }

  const handleButtonClick = () => {
    if (tagToggled) {
      deleteObjectFeedbackTag();
    } else {
      addObjectFeedbackTag();
    }
    handleButtonUpdate();
  }

  return (
    <button className="btn btn-sm mr-1" onClick={handleButtonClick} style={{
      fontWeight: "bold",
      color: "white",
      textShadow: "0 0 2px black, 0 0 2px black, 0 0 2px black, 0 0 2px black, 0 0 2px black, 0 0 2px black",
      backgroundColor: `${tagToggled ? feedbackTag.colorHex : "#000000" }`
    }}>{feedbackTag.name}</button>
  );


}

export default ArticleFeedbackTagButton;