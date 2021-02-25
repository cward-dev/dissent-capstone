import { useEffect, useState } from 'react';
import NewsAPIFeed from './NewsAPIFeed.js';
import Errors from '../Errors.js';

function AddArticlesPage( { user } ) {
  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);

  const fetchArticles = async () => {
    let rawArticles;

    try {
      const response = await fetch("https://newsapi.org/v2/top-headlines?country=us&apiKey=46f374bc4801471fa5acc0956d739b7c");
      const data = await response.json();
      rawArticles = data.articles;
    } catch (error) {
      setErrors(["Something went wrong with our fetch, sorry!"]);
    }

    let newArticles = [];
    let postedTime = new Date();
    postedTime = new Date(postedTime.getTime() - postedTime.getTimezoneOffset() * 60000);

    for (let i = 0; i < rawArticles.length; i++) {

      let publishedTime = new Date(rawArticles[i].publishedAt);
      publishedTime = new Date(publishedTime.getTime() - publishedTime.getTimezoneOffset() * 60000);

      let article = {
        "title": rawArticles[i].title,
        "description": rawArticles[i].description,
        "author": rawArticles[i].author,
        "articleUrl": rawArticles[i].url,
        "articleImageUrl": rawArticles[i].urlToImage,
        "datePublished": publishedTime.toISOString(),
        "datePosted": postedTime.toISOString(),
        "source": {
          "sourceId": null,
          "sourceName": rawArticles[i].source.name,
          "websiteUrl": `${rawArticles[i].url.split('/')[0]}//${rawArticles[i].url.split('/')[2]}/`,
          "description": `${rawArticles[i].source.name} has not been given a description.`
        },
        "posts": []
      };
      newArticles.push(article);
    }
    setArticles(newArticles);
  };

  return (
    <div className="container">
      <Errors errors={errors} />
      <h1 className="d-flex flex-row justify-content-center mb-4">Add New Articles</h1>
      <hr className="mb-4"></hr>
      <NewsAPIFeed articles={articles} setArticles={setArticles} user={user} />
      <button className="col btn btn-danger" onClick={fetchArticles}>Get Articles</button>
    </div>
  );
}

export default AddArticlesPage;