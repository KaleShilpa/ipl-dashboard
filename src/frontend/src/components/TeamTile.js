import { Link } from 'react-router-dom';
import './TeamTile.scss';

const TeamTiles = ({teamName}) => {
    return (  
        <div className="TeamTile">
            <h1>
                <Link to={`/teams/${teamName}`}>{teamName}</Link>
            </h1>
        </div>
    );
}
 
export default TeamTiles;