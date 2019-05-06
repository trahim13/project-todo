package org.trahim.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.trahim.project.domain.ProjectTask;

import java.util.List;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    ProjectTask findByProjectSequence(String projectSequence);
}
