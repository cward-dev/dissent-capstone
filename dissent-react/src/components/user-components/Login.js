import { useState } from 'react';
import Errors from '../Errors';
import jwt_decode from 'jwt-decode'; 

function Login({ handleSetUser }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState([]);

  const login = (token) => {
    const { sub: username, authorities } = jwt_decode(token);
    const roles = authorities.split(',');
    const user = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    }

    handleSetUser(user);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    try{
      const response = await fetch('http://localhost:8080/authenticate', {
        method: 'POST',
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          username,
          password
        })
      });

      if (response.status === 200) {
        const { jwt_token } = await response.json(); 
        login(jwt_token);

      } else if (response.status === 403) {
        throw new Error('Bad username or password')

      } else {
        throw new Error('There was a problem logging in...')

      }

    } catch (err) {
      setErrors([err.message]);
    }
  }

  return (
    <div class="col-7">
      <h2>Login</h2>
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
        </div>
      </form>
    </div>
  );
}

export default Login;