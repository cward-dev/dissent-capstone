import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function ArticleFeed() {

  const TEST_ARTICLE = {
    articleId: 1,
    source: "http://www.google.com",
    title: "Article Title",
    description: "A short description of the article.",
    source: "Google",
    author: "Author",
    articleUrl: "www.google.com",
    articleImageUrl: "https://www.buddhistdoor.net/upload/article/10179/5c3ba1b1aebccea4fd7e4483d81480e2.jpeg",
    datePublished: "2021-01-01",
    timestamp: "2021-02-15",
    topics: [],
    posts: [], 
    tags: []
  };

  return (
    <div className="pt-4">
      <ArticleCard article={TEST_ARTICLE} />
      <ArticleCard article={TEST_ARTICLE} />
      <ArticleCard article={TEST_ARTICLE} />
      <ArticleCard article={TEST_ARTICLE} />
    </div>
  );
}

export default ArticleFeed;