import { useEffect, useState } from 'react';
import NewsAPIFeed from './NewsAPIFeed.js';

function AddArticlesPage( { user } ) {
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
      <NewsAPIFeed articles={articles} user={user} />
    </div>
  );
}

export default AddArticlesPage;