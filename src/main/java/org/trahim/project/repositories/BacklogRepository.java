package org.trahim.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.trahim.project.domain.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String projectIdentifier);
}
