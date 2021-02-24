import { useEffect, useState } from 'react';
import ArticleFeed from './ArticleFeed.js';

function HomePage( { user } ) {
  const [articles, setArticles] = useState([]);
  const [updateArticleDelete, setUpdateArticleDelete] = useState(false);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`${process.env.REACT_APP_API_URL}/api/article`);
        const data = await response.json();
        setArticles(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [updateArticleDelete]);

  return (
    <div className="container">
      <h1 className="d-flex flex-row justify-content-center mb-4">Latest Articles</h1>
      <hr className="mb-4"></hr>
      <ArticleFeed articles={articles} updateArticleDelete={updateArticleDelete} setUpdateArticleDelete={setUpdateArticleDelete} user={user} />
    </div>
  );
}

export default HomePage;