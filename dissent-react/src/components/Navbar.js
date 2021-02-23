import { Link } from 'react-router-dom';
import LogoTextOnly from './images/logo-text-only.png';

function Navbar () {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/"><img src={LogoTextOnly} id="navbar-logo" /></Link>
        <div className="row">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link active" to="/login" aria-current="page">Login</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link active" to="/about" aria-current="page">About</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link active" to="/user">Profile</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link active" to="/user/log-out">Log Out</Link>
            </li>
          </ul>
        </div> 
      </div>
    </nav>
  );
}

export default Navbar;