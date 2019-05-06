package org.trahim.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.trahim.project.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String projectLiader);


}
