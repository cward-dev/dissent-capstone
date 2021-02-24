function FeedbackTagAdminRow ( { feedbackTag, setFeedbackTagToEdit, setFeedbackTagToDelete, setFeedbackTagToReactivate, user } ) {

  const handleEditClick = () => {
    cancelAll();
    setFeedbackTagToEdit(feedbackTag);
  };

  const handleDeleteClick = () => {
    cancelAll();
    setFeedbackTagToDelete(feedbackTag);
  };

  const handleReactivateClick = () => {
    cancelAll();
    setFeedbackTagToReactivate(feedbackTag);
  };

  const cancelAll = () => {
    setFeedbackTagToEdit(null);
    setFeedbackTagToDelete(null);
    setFeedbackTagToReactivate(null);
  };

  return (
    <tr>
      <td scope="row">{feedbackTag.name}</td>
      <td><span className="col btn btn-sm" style={{
      backgroundColor: `${feedbackTag.colorHex}`
        }}>{"\u00a0"}</span></td>
      <td className="text-left d-flex flex-row">
        <button className="btn btn-secondary btn-sm col" onClick={handleEditClick}>Edit</button>
        {feedbackTag.active ? <button className="btn btn-danger btn-sm ml-2 col" onClick={handleDeleteClick}>Inactivate</button> : <button className="btn btn-info btn-sm ml-2 col" onClick={handleReactivateClick}>Reactivate</button>}
      </td>
    </tr>
  );
}

export default FeedbackTagAdminRow;