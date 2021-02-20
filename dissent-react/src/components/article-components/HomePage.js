import { useEffect, useState } from 'react';
import ArticleFeed from './ArticleFeed.js';

function HomePage( { user } ) {
  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/article");
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
      <ArticleFeed articles={articles} />
    </div>
  );
}

export default HomePage;