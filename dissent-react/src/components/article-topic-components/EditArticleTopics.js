import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import ArticleTopic from './ArticleTopic.js';
import Errors from '../Errors.js';

function EditArticleTopics ( { article, topics, setTopics, update, setUpdate, user } ) {

  const [errors, setErrors] = useState([]);
  const [allTopics, setAllTopics] = useState([]);

  useEffect(()=> {
    const getData = async () => {
      let mounted = true;
      
      try {
        const response = await fetch(`http://localhost:8080/api/topic`);

        if (mounted) {
          if (response.status === 200) {
            const data = await response.json();
            setAllTopics(data);
            setErrors([]);
          } else if (response.status === 404) {
            setErrors(["Something went wrong with our database, sorry!"]);
          }
        }
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }

      return () => mounted = false;
    };
    getData();
  }, []);

  const makeArticleTopic = (topic) => {
    return (
      <ArticleTopic key={topic.topicId} topic={topic} article={article} user={user} setErrors={setErrors} topics={topics} setTopics={setTopics} update={update} setUpdate={setUpdate} />
    );
  };

  return (
    <>
      <Errors errors={errors} />
      <div className="d-flex flex-row flex-wrap justify-content-center badge badge-secondary mt-1 mb-3 mx-2 p-1">
        {allTopics.sort((a, b) => (a.topicName > b.topicName) ? 1 : -1).map(topic => makeArticleTopic(topic))}
      </div>
      <hr></hr>
    </>
  );
}

export default EditArticleTopics;