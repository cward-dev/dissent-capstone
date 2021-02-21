import FeedbackTagIcon from './FeedbackTagIcon';
import Article from '../article-components/ArticleCard';
import { PieChart } from 'react-minimal-pie-chart';
import{useState } from 'react';

const COLOR_PALETTE = {
  "Sound": "#00FF00",
  "Fallacious": "#ffa500",
  "Biased": "#E71A43",
  "Other": "#ffff00",
  "No-FeedBack": "#FFFFFF"
};
// JSON
const DEFAULT_TAG = {
  "id": "",
  "userId": "",
  "feedbackTag": {
    "feedbackTagId": 0
  }
};

const DEF_FB_TAG ={
  "feedbackTagId": 0,
  "name": "",
}


function FeedbackForm({ listOfTags,  objectId, feedbackTagObjects }) {
const [tag, setTag] = useState(DEFAULT_TAG);
const [feedbackTagJSON, setFeedbackTag] = useState(DEF_FB_TAG);
const{feedbackTagId, name} = feedbackTagJSON;
const{id, userId, feedbackTag} = tag;
const [errors, setErrors] = useState([]);



  const populateDataSet = new Array();
  let dataPoint = {}

  if (listOfTags != undefined) {
    const { key, value } = listOfTags;

    for (const prop in listOfTags) {
      dataPoint = { name: `${prop}`, value: `${listOfTags[prop]}`, color: COLOR_PALETTE[`${prop}`] }
      populateDataSet.push(dataPoint);
    }
  } else {
    dataPoint = { name: "NO DATA", value: 1, color: COLOR_PALETTE["No-FeedBack"] }
    populateDataSet.push(dataPoint);
  }

  const addFeedbackTag = async (tag) => {
    
    const addedTag = {
      "id": tag.id,
      "userId": tag.userId,
      "feedbackTag": tag.feedbackTag
    };

    const init = {
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(addedTag)
    };

    try {
      const response = await fetch("http://localhost:8080/api/feedback-tag", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  
  }

 

  const handleSubmit = (event) => {
    event.preventDefault();
    tag.id = objectId;
    tag.userId ='103a7d9b-f72b-4469-b1a3-bdba2f6356b4';

    tag.feedbackTag = feedbackTagJSON;
    addFeedbackTag(tag);
    console.log(tag);
  };

  const onChangeHandler = (event) =>{
    event.preventDefault();

    console.log(event.target.name);
    console.log(event.target.value);

    const newFeedbackTagJSON= {...feedbackTagJSON};
    newFeedbackTagJSON[event.target.name] = event.target.value;
    console.log(newFeedbackTagJSON.feedbackTagId);
    setFeedbackTag(newFeedbackTagJSON);
  };



  return (
    <div className="container">

      <div className="row ">
        <div className="col-8">
          <PieChart
            center={[50, 50]}
            data={populateDataSet}
            radius={50}
            label={(data) => data.dataEntry.name}
            lineWidth={55}
            paddingAngle={0}
            viewBoxSize={[100, 100]}
            labelStyle={{
              fontSize: "5px",
              fontColor: "FFFFFA",
              fontWeight: "500"
            }}
            labelPosition={65} />
        </div>
        <div className="col-4">
          <form onSubmit={handleSubmit}>
            <label htmlFor="feedbackTagId"><h3>Feedback</h3></label>
            <select id="feedbackTagId" name="feedbackTagId" 
            onChange={onChangeHandler} value = {feedbackTagId}>
              <option value="">--Give Feedback--</option>
              <option value="1">Sound</option>
              <option value="2">Fallacious</option>
              <option value="3">Biased</option>
            </select>
            <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    </div>
  );

} export default FeedbackForm;