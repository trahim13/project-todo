package org.trahim.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.trahim.project.domain.Backlog;
import org.trahim.project.domain.Project;
import org.trahim.project.domain.ProjectTask;
import org.trahim.project.exceptions.ProjectNotFoundException;
import org.trahim.project.repositories.BacklogRepository;
import org.trahim.project.repositories.ProjectRepository;
import org.trahim.project.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifer, ProjectTask projectTask, String username) {


        Backlog backlog =  projectService.findProjectByIdentifier(projectIdentifer, username).getBacklog();

        System.out.println(backlog);
        projectTask.setBacklog(backlog);

        Integer BacklogSequence = backlog.getPTSequence();

        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);


        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifer);


        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }


        if(projectTask.getPriority()==null||projectTask.getPriority()==0){
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }


    public Iterable<ProjectTask>findBacklogById(String id, String username){

        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        //make sure we are searching on an existing backlog
        projectService.findProjectByIdentifier(backlog_id, username);


        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }


        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }


    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
