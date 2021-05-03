package com.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{
 Team findByTeamName(String teamName);
}
