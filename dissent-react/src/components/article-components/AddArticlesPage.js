import { useEffect, useState } from 'react';
import NewsAPIFeed from './NewsAPIFeed.js';

function AddArticlesPage( { user } ) {
  const [articles, setArticles] = useState([]);
  const [errors, setErrors] = useState([]);

  const getSource = (article) => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/source/name/${article.source.name}`);
        const data = await response.json();
        console.log(data.sourceId)
        return data;
      } catch (error) {
        setErrors(["Something went wrong with our fetch, sorry!"]);
      }
    };
    getData();
  }
  
  const addSource = async (article) => {

    const source = {
      "sourceName": article.source.name,
      "websiteUrl": `${article.url.split('/')[0]}//${article.url.split('/')[2]}/`,
      "description": `${article.source.name} has not been given a description.`
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(source)
    };

    try {
      const response = await fetch("http://localhost:8080/api/source", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();
        const newSource = data;
        return newSource;
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const fetchArticles = () => {
    const getData = async () => {
      let rawArticles;
      try {
        const response = await fetch("http://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=46f374bc4801471fa5acc0956d739b7c");
        const data = await response.json();
        rawArticles = data.articles;
        console.log(rawArticles);
      } catch (error) {
        setErrors(["Something went wrong with our fetch, sorry!"]);
      }

      let newArticles = [];
      const currentTime = new Date();

      for (let i = 0; i < rawArticles.length; i++) {

        const article = {
          "title": rawArticles[i].title,
          "description": rawArticles[i].description,
          "author": rawArticles[i].author,
          "articleUrl": rawArticles[i].url,
          "articleImageUrl": rawArticles[i].urlToImage,
          "datePublished": rawArticles[i].publishedAt,
          "datePosted": currentTime.toString(),
          "source": {
            "sourceId": null,
            "sourceName": null,
            "websiteUrl": null,
            "description": null
          },
          "posts": []
        };
  
        let source = getSource(rawArticles[i]);

        if (!source) {
          source = addSource(rawArticles[i]);
        }

        article.source = source;

        newArticles.push(article);
        
      }
  
      console.log(newArticles);
      setArticles(newArticles);
    };
    getData();
  };


  return (
    <div className="container">
      <button className="btn btn-danger" onClick={fetchArticles}>Get Articles</button>
      <NewsAPIFeed articles={articles} user={user} />
    </div>
  );
}

export default AddArticlesPage;