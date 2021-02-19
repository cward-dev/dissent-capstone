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
      <div className="alert alert-dark mb-4 text-center">
        <h2>Latest Articles</h2>
      </div>
      {articles.sort((a, b) => (new Date(a.datePosted) < new Date(b.datePosted)) ? 1 : -1).map(article => makeArticle(article))}
    </div>
  );
}

export default ArticleFeed;