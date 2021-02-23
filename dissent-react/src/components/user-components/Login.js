import { useState } from "react";
 
function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
    }

    return(
        <div>
            <h2>Login</h2>
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
                    <button type="submit" class="btn btn-primary my-3">Login</button>
                </div>
            </form>
        </div>
    );
}

export default Login;