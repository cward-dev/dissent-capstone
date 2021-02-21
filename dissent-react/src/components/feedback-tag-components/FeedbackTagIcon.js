import Article from '../article-components/ArticleCard';
import FeedbackForm from './feedbackForm';
import {useState , useEffect} from 'react';




const COLOR_PALETTE = {
  "Sound": "#00FF00",
  "Fallacious": "#ffa500",
  "Biased": "#E71A43",
  "Other": "#ffff00"
};


 
function FeedbackTagIcon({ data , id }) {
  //this is list of names of Tags
const[feedbackTagNames, setTags] = useState(null);
const [errors, setErrors] = useState([]);

  useEffect(() => {

    const getFeedbackTagObject = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/feedback-tag");
        const data = await response.json();
        setTags(data);
      } catch (error) {
        setErrors(["Something went wrong with our database, sorry!"]);
      }
    };
    getFeedbackTagObject();
  }, []);


  const addFeedbackTag = function(addedTag){
    const tag = {
      "id": addedTag.id,
      "userId": addedTag.userId,
      "feedbackTag": addedTag.feedbackTag
    };

  
    const init = {
      method : "POST",
      headers: {
        "Content-Type" : "application/json",
        "Accept" : "application/json"
      },
      body : JSON.stringify(tag)
    }

    fetch("http://localhost:8080/api/article/feedback-tag", init)
    .then(response => {
      if(response.status===201 || response.status === 400){
        return response.json();
      } else {
        Promise.reject("Something went wrong")
      }
  })
  .then(data => {
    if(data.aliasId){
      setAliases([...aliases, data])
      setErrors([]);
      } else{
        setErrors(data);
      }
    })
  .catch(error => console.log(error));
    
  }



let [isClicked, setClick] = useState(false);

 let handleClick = (event) =>{
   if(isClicked === false){
     setClick(true);
   }else{
     setClick(false);
   }
  }
 
  return(
    <div>
    <button onClick={handleClick} ><img className="mr-3 rounded-circle tag-pie-chart" src="http://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Pie-Chart-PNG-Image.png" /></button>
    {isClicked ? <FeedbackForm listOfTags = {data} objectId = {id} feedbackTagObjects = {feedbackTagNames} /> : null}
    </div>
  );

}
export default FeedbackTagIcon;