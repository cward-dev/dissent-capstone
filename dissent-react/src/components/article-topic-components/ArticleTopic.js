import { useState } from "react";

function ArticleTopic ( { topic, article, setErrors, topics, setTopics, update, setUpdate } ) {

  const [articleTopicExists, setArticleTopicExists] = useState(article.topics.some(t => t.topicId === topic.topicId));

  const addArticleTopic = async () => {
    let mounted = true;

    const articleTopicToAdd = {
      "articleId": article.articleId,
      "topicId": topic.topicId
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(articleTopicToAdd)
    };

    try {
<<<<<<< HEAD
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/article/topic`, init);
=======

      const response = await fetch("http://localhost:8080/api/article/topic", init);
>>>>>>> main

      if (mounted) {
        if (response.status === 201) {
          setArticleTopicExists(true);
          const newTopics = [...topics, topic];
          setTopics(newTopics);
          setErrors([]);
        } else {
          throw new Error(["Something unexpected went wrong, sorry!"]);
        }
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }

    return () => mounted = false;
  }

  const deleteArticleTopic = async () => {

    try {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/api/article/topic/${article.articleId}/${topic.topicId}`, { method: "DELETE"} );

      if (response.status === 204) {
        setArticleTopicExists(false);
        const newTopics = topics.filter(t => t.getId != topic.topicId);
        setTopics(newTopics)
        setErrors([]);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const handleClick = () => {
    if (articleTopicExists) {
      deleteArticleTopic();
      update === true ? setUpdate(false) : setUpdate(true);
    } else {
      addArticleTopic();
    }
  };

  return (
    <button key={topic.topicId} type="button" className={`btn btn-sm ${articleTopicExists ? "btn-light" : "btn-dark"} m-1`} onClick={handleClick}>{topic.topicName}</button>
  );
}

export default ArticleTopic;