import {React} from 'react';
import { Link } from 'react-router-dom';
import './MatchSmallCard.scss';
const MatchSmallCard = ({match, teamName}) => {
    const otherTeam = match.team1 === teamName ? match.team2 : match.team1;
    const isMatchWon = teamName === match.winner;
    return (
        <div className={isMatchWon?'MatchSmallCard won-card':'MatchSmallCard lost-card'}>
            <span className='versus'>vs</span>
            <h3><Link to={`/teams/${otherTeam}`}>{otherTeam}</Link></h3>
            <p className='match-result'>{match.winner} won by {match.resultMargin} {match.result}</p>
        </div>
    );
}
 
export default MatchSmallCard;