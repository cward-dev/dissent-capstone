import {useState , useEffect} from 'react';
import { PieChart } from 'react-minimal-pie-chart';
import '../FeedbackTagIcon.css';
 
function UserFeedbackTagIcon( { setErrors, thisUser, user } ) {

  const [feedbackTags, setFeedbackTags] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/user/${thisUser.userId}/feedback-tag`);
        const data = await response.json();
        setFeedbackTags(data);
      } catch (error) {
        console.log(error);
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getData();
  }, [thisUser]);
 
  return(
    <div className="d-flex flex-row justify-content-start">
        <div className={`container feedbackTagIconUser`}>
          {feedbackTags && feedbackTags.length > 0 ? 
          <PieChart
            center={[50, 50]}
            animate
            animationDuration={500}
            data={feedbackTags}
            radius={50}
            viewBoxSize={[100, 100]}
          /> : 
          <PieChart
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
export default UserFeedbackTagIcon;