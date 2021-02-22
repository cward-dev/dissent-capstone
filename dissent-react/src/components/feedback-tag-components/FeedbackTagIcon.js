import {useState , useEffect} from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import Article from '../article-components/ArticleCard';
import FeedbackForm from './FeedbackTagForm';
import './FeedbackTagIcon.css';
 
function FeedbackTagIcon( { feedbackTagMenuDisplayed, setFeedbackTagMenuDisplayed, object, user } ) {

  let handleClick = () => {
    if (feedbackTagMenuDisplayed) {
      setFeedbackTagMenuDisplayed(false);
    } else {
      setFeedbackTagMenuDisplayed(true);
    }
  }
 
  return(
    <div>
        <div className={`container ${object.postId ? "feedbackTagIconPost" : "feedbackTagIconArticle" }`}>
          {object.feedbackTags && object.feedbackTags.length > 0 ? 
          <PieChart
            onClick={handleClick}
            center={[50, 50]}
            animate
            animationDuration={500}
            data={object.feedbackTags}
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
  );

}
export default FeedbackTagIcon;