import { useContext, useState } from 'react';
import Errors from '../Errors';
import { Link, useHistory, useLocation } from 'react-router-dom';
import AuthContext from '../AuthContext'

function Login() {
  const auth = useContext(AuthContext);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState([]);

  const history = useHistory();
  const location = useLocation();

  const { state: { from } = { from : '/' } } = location;


  const handleSubmit = async (event) => {
    event.preventDefault();

    try{
      await auth.authenticate(username, password)
      history.push(from);
    } catch (err) {
      setErrors([err.message]);
    }
  }

  return (
    <div>
      <h2 className="mb-3">Login</h2>
      <Errors errors={errors} />
      <form onSubmit={handleSubmit}>
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" class="form-control" onChange={(event) => setUsername(event.target.value)} />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" class="form-control" onChange={(event) => setPassword(event.target.value)} />
        </div>
        <div>
          <button type="submit" class="btn btn-dark my-3">Login</button>
          <button type="submit" class="btn btn-dark my-3 mx-3"><Link to={from}>Cancel</Link></button>
        </div>
      </form>
    </div>
  );
}

export default Login;