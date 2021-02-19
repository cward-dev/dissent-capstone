function Errors( { errors } ) {
  if (errors.length === 0) {
    return null;
  }

  return (
    <div className="alert alert-danger">
      The following errors were found:
      <ul>
        {errors.map(error => (
          <li key={error}>{error}</li>
        ))}
      </ul>
    </div>
  );
}

export default Errors;