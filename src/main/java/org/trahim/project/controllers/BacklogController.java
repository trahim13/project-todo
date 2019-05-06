package org.trahim.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.trahim.project.domain.ProjectTask;
import org.trahim.project.services.MapValidationErrorService;
import org.trahim.project.services.ProjectTaskService;

import javax.validation.Valid;
import java.security.Principal;


@CrossOrigin
@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{project_identifier}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                                     @PathVariable String project_identifier, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask1 = projectTaskService.addProjectTask(project_identifier, projectTask, principal.getName());
        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{project_identifier}")
    public Iterable<ProjectTask> getProjectTasks(@PathVariable String project_identifier, Principal principal) {
        return projectTaskService.findBacklogById(project_identifier, principal.getName());
    }

    @GetMapping("/{project_identifier}/{project_sequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String project_identifier,
                                            @PathVariable String project_sequence, Principal principal) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(project_identifier, project_sequence, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{project_identifier}/{project_sequence}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedTask, BindingResult result,
                                               @PathVariable String project_identifier, @PathVariable String project_sequence,
                                               Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask = projectTaskService.updateByProjectSequence(updatedTask, project_identifier, project_sequence, principal.getName());

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }


    @DeleteMapping("/{project_identifier}/{project_sequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String project_identifier, @PathVariable String project_sequence,
                                               Principal principal) {
        projectTaskService.deletePTByProjectSequence(project_identifier, project_sequence, principal.getName());

        return new ResponseEntity<String>("Project Task " + project_sequence + " was deleted successfully", HttpStatus.OK);
    }
}
