package com.javaexpress.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaexpress.ipldashboard.model.Team;

@Component
@Transactional
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final EntityManager em;

	@Autowired
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}

	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
						
			Map<String, Team> teamData = new HashMap<String, Team>();
			
			
			em.createQuery("select m.team1, count(*) from Match m group by m.team1",Object[].class)
			.getResultList()
			.stream()
			.map(e->new Team((String)e[0],(long)e[1]))
			.forEach(team->teamData.put(team.getTeamName(), team));
			
			em.createQuery("select m.team2, count(*) from Match m group by m.team2",Object[].class)
			.getResultList()
			.stream()
			.forEach(e->{
				Team team = teamData.get((String)e[0]);
				team.setTotalMatches(team.getTotalMatches()+(long) e[1]);
			});
			
			em.createQuery("select m.winner, count(*) from Match m group by m.winner",Object[].class)
			.getResultList()
			.stream()
			.forEach(e->{
				Team team = teamData.get((String)e[0]);
				if(team!=null) team.setTotalWins((long) e[1]);
			});
			
			teamData.values().forEach(team->em.persist(team));
			teamData.values().forEach(team->System.out.println(team));
		}

	}

}