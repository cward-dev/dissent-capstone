import { Link } from 'react-router-dom';
import AddPost from '../post-components/AddPost';
import FeedbackTagIcon from '../feedback-tag-components/FeedbackTagIcon';
import './ArticleCard.css';

function ArticleCard ( { article, articleOpen } ) {

  const { articleId, title, description, author, articleUrl, articleImageUrl, datePublished, datePosted, source, topics, posts, feedbackTags } = article;
  const timestampDate = (new Date(article.datePosted)).toISOString().split('T')[0];
  const timestampTime = (new Date(article.datePosted)).toISOString().split('T')[1];

  return (
    <div className="card flex-row flex-wrap text-white bg-dark mb-4">
      <div className="border-0">
        <a href={articleUrl}><img src={articleImageUrl} className="card-img-top" /></a>
      </div>
      <div className="col p-3">
        <a href={articleUrl}><h4 className="card-title">{title}</h4></a>
        <p className="card-text">{description}</p>
        <div className="row">
          <div className="col">
            <p className="source-author-line card-text"><a className="source-link" href={source.websiteUrl}>{source.sourceName}</a>. {author}</p>
          </div>
          <div className="col text-right">
            <p className="source-author-line card-text">{timestampDate === Date.now() ? timestampTime : timestampDate}</p>
          </div>
        </div>
      </div>
      <div className="card-footer w-100 text-muted px-1">
        <div className="row">  
          <div className="col pl-4">
             {/* I also need a userID */}
            <FeedbackTagIcon data = {feedbackTags} id = {articleId}/>
          </div>
          <div className="col text-right">
            {articleOpen ? <AddPost articleId={articleId} /> : <Link className="btn btn-secondary px-2 py-1 mr-2" to={`/article/${articleId}`}>Discussions ({posts.length})</Link>}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ArticleCard;