import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import Errors from '../Errors.js';
import ArticleCard from './ArticleCard.js';
import AddPost from '../post-components/AddPost.js';
import PostFeed from '../post-components/PostFeed.js';

const PLACEHOLDER_ARTICLE = {
  "title": "Loading",
  "description": "Description",
  "sourceId": "sourceid",
  "author": "Author",
  "articleUrl": "https://www.google.com",
  "articleImageUrl": "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
  "datePublished": "2021-2-10",
  "datePosted": "2021-2-10",
  "source": {
    "sourceId": "",
    "sourceName": "",
    "websiteUrl": "",
    "description": ""
  },
  "posts": []
};

function ArticlePage ( { user } ) {

  const { articleId } = useParams();
  const [article, setArticle] = useState(PLACEHOLDER_ARTICLE);
  const [addPost, setAddPost] = useState(false);
  const [errors, setErrors] = useState([]);
  const history = useHistory();

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/article/${articleId}`);
      const data = await response.json();
      setArticle(data);
    } catch (error) {
      setErrors(["Something went wrong with our database, sorry!"]);
    }
  };

  useEffect(() => {
    getData();
  }, []);

  const handlePostAdded = () => {
    getData();
  }

  return (
    <div>
      <Errors errors={errors} />
      <ArticleCard articleId={articleId} articleOpen={true} setAddPost={setAddPost} user={user} />
      <hr></hr>
      {addPost ? <AddPost addPost={addPost} setAddPost={setAddPost} articleId={articleId} user={user} handlePostAdded={handlePostAdded} /> : null}
      <PostFeed posts={article.posts} user={user} handlePostAdded={handlePostAdded} />
    </div>
  );
}

export default ArticlePage;