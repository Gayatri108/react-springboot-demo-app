package com.test.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Match;
import com.test.model.Team;
import com.test.repository.MatchRepository;
import com.test.repository.TeamRepository;

@RestController
public class TeamController {

	TeamRepository teamRepo;
	MatchRepository matchRepo;
	
	public TeamController(TeamRepository teamRepo,MatchRepository matchRepo) {
		super();
		this.teamRepo = teamRepo;
		this.matchRepo = matchRepo;
	}

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
	Team team =this.teamRepo.findByTeamName(teamName);
	
	List<Match> byTeam1OrTeam2 = this.matchRepo.getfindLastestMatchByTeamNameMatch(teamName,4);
	team.setMatches(byTeam1OrTeam2);
	return team;
	}

}
