import {useContext, useState} from 'react';
import { Link } from 'react-router-dom';
import LogoTextOnly from './images/logo-text-only.png';
import AuthContext from './AuthContext';

function Navbar ({user, handleLogout}) {
  const auth = useContext(AuthContext);
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/"><img src={LogoTextOnly} id="navbar-logo" /></Link>
        <div className="row">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            {!auth.user 
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
                  <Link className="nav-link active" to={`/user/${auth.user.username}`}>{auth.user.username}</Link>
                </li>
            }
            <li className="nav-item">
              <Link className="nav-link active" to="/about" aria-current="page">About</Link>
            </li>
            {auth.user && 
              <li className="nav-item">
                <Link className="nav-link active" onClick={auth.logout} to="/">Log Out</Link>
              </li>
            }
          </ul>
        </div> 
      </div>
    </nav>
  );
}

export default Navbar;