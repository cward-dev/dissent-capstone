import ArticleCard from './ArticleCard.js';
import './ArticleFeed.css';

function ArticleFeed( { articles } ) {

  const makeArticle = (article) => {
    return (
      <ArticleCard key={article.articleId} article={article} />
    );
  };

  return (
    <div>
      {articles.map(article => makeArticle(article))}
    </div>
  );
}

export default ArticleFeed;