import {useState , useEffect} from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import ArticleFeedbackTagForm from './ArticleFeedbackTagForm';
import '../FeedbackTagIcon.css';
 
function ArticleFeedbackTagIcon( { setErrors, article, user } ) {

  const [feedbackTags, setFeedbackTags] = useState([]);
  const [update, setUpdate] = useState(false);
  const [feedbackTagMenuDisplayed, setFeedbackTagMenuDisplayed] = useState(false);

  const getData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/article/feedback-tag/article/${article.articleId}`);
      const data = await response.json();
      setFeedbackTags(data);
    } catch (error) {
      console.log(error);
      setErrors(["Something went wrong with our database, sorry!"]);
    }
  };

  useEffect(() => {
    getData();
  }, [article, update]);

  let handleClick = () => {
    if (!user) {
      return;
    }
    if (feedbackTagMenuDisplayed) {
      setFeedbackTagMenuDisplayed(false);
    } else {
      setFeedbackTagMenuDisplayed(true);
    }
  }

  const handleTagClick = () => {
    getData();
    // setFeedbackTagMenuDisplayed(false);
  }
 
  return(
    <div className="d-flex flex-row align-items-start">
      <div>
        <div className={`container feedbackTagIconArticle`}>
          {feedbackTags && feedbackTags.length > 0 ? 
          <PieChart
            onClick={handleClick}
            center={[50, 50]}
            animate
            animationDuration={500}
            data={feedbackTags}
            radius={50}
            viewBoxSize={[100, 100]}
          /> : 
          <PieChart
            onClick={handleClick}
            center={[50, 50]}
            animate
            animationDuration={500}
            data={[{
              "title": "empty",
              "value": 1,
              "color": "#000000"
            }]}
            radius={50}
            viewBoxSize={[100, 100]}
          />}
        </div>
      </div>
      {feedbackTagMenuDisplayed ? <ArticleFeedbackTagForm object={article} user={user} handleTagClick={handleTagClick} update={update} setUpdate={setUpdate} /> : null}
    </div>
  );

}
export default ArticleFeedbackTagIcon;