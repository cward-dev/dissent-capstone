import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import ArticleTopic from './ArticleTopic.js';
import Errors from '../Errors.js';

function EditArticleTopics ( { article, topics, setTopics, user } ) {

  const [errors, setErrors] = useState([]);
  const [allTopics, setAllTopics] = useState([]);

  useEffect(()=> {
    const getData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/topic`);

        if (response.status === 200) {
          const data = await response.json();
          setAllTopics(data);
          setErrors([]);
        } else if (response.status === 404) {
          setErrors(["Something went wrong with our database, sorry!"]);
        }
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  },[]);

  const makeArticleTopic = (topic) => {
    return (
      <ArticleTopic key={topic.topicId} topic={topic} article={article} user={user} setErrors={setErrors} topics={topics} setTopics={setTopics} />
    );
  };

  return (
    <>
      <Errors errors={errors} />
      <div className="d-flex flex-row justify-content-center alert alert-dark mt-1 mb-3 mx-2 p-1">
        {allTopics.sort((a, b) => (a.topicName > b.topicName) ? 1 : -1).map(topic => makeArticleTopic(topic))}
      </div>
      <hr></hr>
    </>
  );
}

export default EditArticleTopics;