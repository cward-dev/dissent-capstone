import { useEffect, useState } from 'react';
import NewsAPIFeed from './NewsAPIFeed.js';
import Errors from '../Errors.js';

function AddArticlesPage( { user } ) {
  const [articles, setArticles] = useState([]);
  const [sources, setSources] = useState([]);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/source`);
        const data = await response.json();
        setSources(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);
  
  const addSource = (article) => { // does this still run as async since it's called in fetchArticles?

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
      const response = fetch("http://localhost:8080/api/source", init);

      if (response.status === 201 || response.status === 400) {
        const data = response.json();
        const newSource = data;
        let newSources = [...sources, newSource];
        setSources(newSources);
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(["Something unexpected went wrong, sorry!"]);
    }
  }

  const fetchArticles = async () => {
    let rawArticles;
    try {
      const response = await fetch("http://newsapi.org/v2/top-headlines?country=us&apiKey=46f374bc4801471fa5acc0956d739b7c");
      const data = await response.json();
      rawArticles = data.articles;
      console.log(rawArticles);
    } catch (error) {
      setErrors(["Something went wrong with our fetch, sorry!"]);
    }

    let newArticles = [];
    let postedTime = new Date();
    postedTime = new Date(postedTime.getTime() - postedTime.getTimezoneOffset() * 60000);
    let sourcesListForFetch = [...sources];

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
          "websiteUrl": null,
          "description": null
        },
        "posts": []
      };

      let source = sourcesListForFetch.find(s => s.sourceName === article.source.sourceName);

      if (!source) {
        addSource(rawArticles[i]); // TODO how do I get the source to add to list in loop? The setter is async?
        source = sourcesListForFetch.find(s => s.sourceName === rawArticles[i].source.name)
      }

      if (source) {
        article.source.sourceId = source.sourceId;
      }

      newArticles.push(article);
      
    }

    console.log(newArticles);
    setArticles(newArticles);
  };

  return (
    <div className="container">
      <Errors errors={errors} />
      <NewsAPIFeed articles={articles} setArticles={setArticles} user={user} />
      <button className="col btn btn-danger" onClick={fetchArticles}>Get Article</button>
    </div>
  );
}

export default AddArticlesPage;