package com.test.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.test.model.Team;

@Component
public class JobCompletionListner extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionListner.class);

	private final EntityManager em;
	Map<String, Team> teamData = new HashMap<>();

	@Autowired
	public JobCompletionListner(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");
			em.createQuery("select m.team1,count(*) from Match m group by m.team1 ", Object[].class).getResultList()
					.stream()
					.map(e -> new Team((String) e[0], (long) e[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));
			em.createQuery("select m.team2,count(*) from Match m group by m.team2 ", Object[].class).getResultList()
			.stream()
			.forEach(e->{
				Team team=teamData.get((String)e[0]);
				team.setTotalMatches(team.getTotalMatches()+(long)e[1]);
			});
			em.createQuery("select m.winner,count(*) from Match m group by m.winner ", Object[].class).getResultList()
			.stream()
			.forEach(e->{
				Team team=teamData.get((String)e[0]);
				if(team!=null)team.setTotalWins((long)e[1]);
			});
			teamData.values().forEach(team->em.persist(team));
			teamData.values().forEach(team->System.out.println(team));
			

		}
	}
}
