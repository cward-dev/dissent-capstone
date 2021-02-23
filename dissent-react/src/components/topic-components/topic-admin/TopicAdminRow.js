function TopicAdminRow ( { topic, setTopicToEdit, setTopicToDelete, setTopicToReactivate, user } ) {

  const handleEditClick = () => {
    cancelAll();
    setTopicToEdit(topic);
  };

  const handleDeleteClick = () => {
    cancelAll();
    setTopicToDelete(topic);
  };

  const handleReactivateClick = () => {
    cancelAll();
    setTopicToReactivate(topic);
  };

  const cancelAll = () => {
    setTopicToEdit(null);
    setTopicToDelete(null);
    setTopicToReactivate(null);
  };

  return (
    <tr>
      <td scope="row">{topic.topicName}</td>
      <td>{topic.articles.length}</td>
      <td className="text-left d-flex flex-row">
        <button className="btn btn-secondary btn-sm col" onClick={handleEditClick}>Edit</button>
        {topic.active ? <button className="btn btn-danger btn-sm ml-2 col" onClick={handleDeleteClick}>Inactivate</button> : <button className="btn btn-info btn-sm ml-2 col" onClick={handleReactivateClick}>Reactivate</button>}
      </td>
    </tr>
  );
}

export default TopicAdminRow;