package com.test.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.test.model.Match;

public class MatchProcessor implements ItemProcessor<MatchInput, Match> {
	private static final Logger log = LoggerFactory.getLogger(MatchProcessor.class);

	@Override
	public Match process(MatchInput item) throws Exception {
		Match match = new Match();
		match.setId(Long.parseLong(item.getId()));
		match.setCity(item.getCity());
		match.setDate(LocalDate.parse(item.getDate()));
		match.setPlayerOfMatch(item.getPlayer_of_match());
		match.setVenue(item.getVenue());
		String firstInningsTeam, secondInningsTeam;
		if ("bat".equals(item.getToss_decision())) {
			firstInningsTeam = item.getToss_winner();
			secondInningsTeam = item.getToss_winner().equals(item.getTeam1()) ? item.getTeam2() : item.getTeam1();
		} else {
			secondInningsTeam = item.getToss_winner();
			firstInningsTeam = item.getToss_winner().equals(item.getTeam1()) ? item.getTeam2() : item.getTeam1();
		}
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		match.setTossDecision(item.getToss_decision());
		match.setTossWinner(item.getToss_winner());
		match.setResult(item.getResult());
		match.setResultMargin(item.getResult_margin());
		match.setWinner(item.getWinner());
		match.setUmpire1(item.getUmpire1());
		match.setUmpire2(item.getUmpire2());

		return match;
	}

}
