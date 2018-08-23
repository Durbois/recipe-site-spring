package com.teamtreehouse.recipesitespring.dao;

import com.teamtreehouse.recipesitespring.domain.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepDao extends CrudRepository<Step, Long> {
}
