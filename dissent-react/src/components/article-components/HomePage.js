import { useEffect, useState } from 'react';
import ArticleFeed from './ArticleFeed.js';

function HomePage( { user } ) {
  const [articles, setArticles] = useState([]);
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
  }, []);

  return (
    <div className="container">
      <ArticleFeed articles={articles} user={user} />
    </div>
  );
}

export default HomePage;