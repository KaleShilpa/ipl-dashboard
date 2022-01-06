import {React,useEffect,useState} from 'react';
import { useParams } from 'react-router-dom';
import MatchDetailCard from '../components/MatchDetailCard';
import YearSelector from '../components/YearSelector';
import './MatchPage.scss';


const MatchPage = () => {

    const [matches, setMatches] = useState([]);
    const {teamName, year} = useParams();

    useEffect(
        ()=>{

            const fetchMatches = async()=>{
                const response = await fetch(`http://localhost:4000/teams/${teamName}/matches?year=${year}`);
                const data = await response.json();
                setMatches(data);
            };

            fetchMatches();
        },[teamName,year]

    );

    if(!matches){
        return <h1>Match not found</h1>
    }    
    return (
        <div className="MatchPage">
             <div className="year-selector">
                 <h3>Select Year</h3>
                 <YearSelector teamName={teamName}/>
             </div>
             <div>
                <h1>{teamName} matches in {year}</h1>
                 {matches.map(match=><MatchDetailCard  teamName={teamName} match={match} key={match.id}/>)}          
            </div>
        </div>
    );
}
 
export default MatchPage;