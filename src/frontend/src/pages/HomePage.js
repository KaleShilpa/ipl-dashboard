import {React,useEffect,useState} from 'react';
import TeamTile from '../components/TeamTile';
import './HomePage.scss';

const HomePage = () => {

    const [teams, setTeams] = useState([]);

    useEffect(
        ()=>{

            const fetchTeams = async()=>{
                const response = await fetch(`http://localhost:4000/teams`);
                const data = await response.json();
                setTeams(data);
                
            };

            fetchTeams();
        },[]

    );

    
    return (
        <div className="HomePage">
            <h1>IPL DashBoard</h1>
            <div className="team-grid">
                {teams.map(team => <TeamTile teamName={team.teamName} key={team.id}/>)} 
            </div> 
            
            
        </div>
    );
}
 
export default HomePage;