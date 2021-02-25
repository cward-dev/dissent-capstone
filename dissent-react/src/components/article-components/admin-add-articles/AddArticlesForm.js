import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from '../../Errors.js';

function AddArticlesForm ( { setArticles, setAddArticles, user } ) {

  const [articlesJson, setArticlesJson] = useState("");
  const [errors, setErrors] = useState([]);

  const handleChange = (event) => {
    const updatedArticlesJson = event.target.value;
    setArticlesJson(updatedArticlesJson);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    let rawArticles;

    try {
      const data = JSON.parse(articlesJson);
      rawArticles = data.articles;

      // rawArticles = JSON.parse(articlesJson);
    } catch (error) {
      setErrors(["Something went wrong with our fetch, sorry!"]);
    }

    let newArticles = [];
    let postedTime = new Date();
    postedTime = new Date(postedTime.getTime() - postedTime.getTimezoneOffset() * 60000);

    console.log(rawArticles.length);
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
    setAddArticles(false);
  }

  const handleCancel = () => {
    setAddArticles(false);
  }

  return (
    <form onSubmit={handleSubmit}>
      <Errors errors={errors} />
      <div className="form-row mb-4">
        <div className="col-9">
          <label htmlFor="articlesJson" className="pl-2 pt-2">New Articles Json Response (no subscription)</label>
        </div>
        <textarea className="form-control mb-3 mr-3" id="articlesJson" name="articlesJson" type="textarea" rows="10" required onChange={handleChange} />
        <div className="col text-right">
          <button type="button" className="btn btn-light btn-sm" onClick={handleCancel}>Cancel</button>
          <button type="submit" className="btn btn-dark ml-2 mr-3 btn-sm">Submit</button>
        </div>
      </div>
      <hr></hr>
    </form>
  );
}

export default AddArticlesForm;