package com.hvl.feedApp.repository;

import com.hvl.feedApp.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    public Agent getByUsername(String username);
}

