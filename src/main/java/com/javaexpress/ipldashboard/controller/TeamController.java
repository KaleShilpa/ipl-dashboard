package com.javaexpress.ipldashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import com.javaexpress.ipldashboard.model.Match;
import com.javaexpress.ipldashboard.model.Team;
import com.javaexpress.ipldashboard.repository.MatchRepository;
import com.javaexpress.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@GetMapping("/teams/{teamName}")
	public Team getTeam(@PathVariable("teamName") String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		team.setMatches(matchRepository.findLatestmatchesByTeam(teamName,4));
		return team;
		
	}

	@GetMapping("/teams")
	public Iterable<Team> getAllTeams() {
		return this.teamRepository.findAll();
		
	}

	@GetMapping("/teams/{teamName}/matches")
	public List<Match> getMatchesByTeam(@PathVariable("teamName") String teamName, @RequestParam("year") int year) {
		
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year+1, 1, 1);

		return matchRepository.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
			teamName, startDate, endDate, teamName, startDate, endDate);
		 
		
	}

}
