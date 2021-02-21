import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../Errors.js';
import ArticleCard from './ArticleCard.js';
import AddPost from '../post-components/AddPost.js';
import PostFeed from '../post-components/PostFeed.js';

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

function ArticlePage ( { user } ) {
  const { articleId } = useParams();
  const [posts, setPosts] = useState([]);
  const [article, setArticle] = useState(PLACEHOLDER_ARTICLE);
  const [addPost, setAddPost] = useState(false);
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
      <ArticleCard articleOpen={true} article={article} setAddPost={setAddPost} />
      <hr></hr>
      {addPost ? <AddPost addPost={addPost} setAddPost={setAddPost} articleId={articleId} user={user} /> : null}
      <PostFeed posts={article.posts} user={user} />
    </div>
  );
}

export default ArticlePage;