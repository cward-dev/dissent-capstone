function TopicAdminRow ( { topic, setTopicToEdit, setTopicToDelete, setTopicToReactivate, user } ) {

  const handleEditClick = () => {
    setTopicToEdit(topic);
    setTopicToDelete(null);
    setTopicToReactivate(null);
  };

  const handleDeleteClick = () => {
    setTopicToDelete(topic);
    setTopicToEdit(null);
    setTopicToReactivate(null);
  };

  const handleReactivateClick = () => {
    setTopicToReactivate(topic);
    setTopicToDelete(null);
    setTopicToEdit(null);
  };

  return (
    <tr>
      <th scope="row">{topic.topicId}</th>
      <td>{topic.topicName}</td>
      <td>{topic.active ? "Yes" : "No"}</td>
      <td>{topic.articles.length}</td>
      <td className="text-left d-flex flex-row">
        <button className="btn btn-secondary btn-sm col" onClick={handleEditClick}>Edit</button>
        {topic.active ? <button className="btn btn-danger btn-sm ml-2 col" onClick={handleDeleteClick}>Inactivate</button> : <button className="btn btn-info btn-sm ml-2 col" onClick={handleReactivateClick}>Reactivate</button>}
      </td>
    </tr>
  );
}

export default TopicAdminRow;