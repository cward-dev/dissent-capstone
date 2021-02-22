function Errors( { errors } ) {
  if (errors.length === 0) {
    return null;
  }

  return (
    <div className="alert alert-danger p-2">
      {errors ? <ul>
        {errors.map(error => (
          <li key={error}>{error}</li>
        ))}
      </ul>
      : null}
    </div>
  );
}

export default Errors;