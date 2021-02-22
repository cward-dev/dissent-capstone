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
      <button onClick={handleClick} className="btn btn-link btn-small">
        <div className="container feedbackTagIcon">
          {object.feedbackTags && object.feedbackTags.length > 0 ? 
          <PieChart
            center={[1, 1]}
            animate
            animationDuration={500}
            data={object.feedbackTags}
            radius={1}
            viewBoxSize={[2, 2]}
          /> : 
          <PieChart
            center={[1, 1]}
            animate
            animationDuration={500}
            data={[{
              "title": "empty",
              "value": 1,
              "color": "#000000"
            }]}
            radius={1}
            viewBoxSize={[2, 2]}
          />}
        </div>
        </button>
    </div>
  );

}
export default FeedbackTagIcon;