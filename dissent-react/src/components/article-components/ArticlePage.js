import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../Errors.js';
import ArticleCard from './ArticleCard.js';
import PostList from '../post-components/PostList.js';

const PLACEHOLDER_ARTICLE = {
  "title": "PlaceHolder",
  "description": "Description",
  "sourceId": "sourceid",
  "author": "Author",
  "articleUrl": "http://www.google.com",
  "articleImageUrl": "http://www.google.com",
  "datePublished": "2021-2-10",
  "datePosted": "2021-2-10",
  "source": {
    "sourceId": null,
    "sourceName": null,
    "websiteUrl": null,
    "description": null
  },
  "posts": []
};

function ArticlePage () {
  const { articleId } = useParams();
  const [article, setArticle] = useState(PLACEHOLDER_ARTICLE);
  const [errors, setErrors] = useState([]);
  const history = useHistory();

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/article/${articleId}`);
        const data = await response.json();
        setArticle(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, []);

  return (
    <div>
      <ArticleCard articleOpen={true} article={article} />
      <PostList posts={article.posts} />
    </div>
  );
}

export default ArticlePage;