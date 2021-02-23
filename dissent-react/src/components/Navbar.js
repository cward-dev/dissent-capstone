import { Link } from 'react-router-dom';
import LogoTextOnly from './images/logo-text-only.png';

function Navbar ({user, handleLogout}) {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/"><img src={LogoTextOnly} id="navbar-logo" /></Link>
        <div className="row">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            {!user 
              ?
              <>
                <li className="nav-item">
                  <Link className="nav-link active" to="/login" aria-current="page">Login</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link active" to="/register" aria-current="page">Register</Link>
                </li>
              </>
              : 
                <li className="nav-item">
                  <Link className="nav-link active" to="/user">{user.username}</Link>
                </li>
            }
            <li className="nav-item">
              <Link className="nav-link active" to="/about" aria-current="page">About</Link>
            </li>
            {user && 
              <li className="nav-item">
                <Link className="nav-link active" onClick={handleLogout} to="/">Log Out</Link>
              </li>
            }
          </ul>
        </div> 
      </div>
    </nav>
  );
}

export default Navbar;