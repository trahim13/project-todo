package org.trahim.project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required.")
    private String projectName;

    @NotBlank(message = "Project identifier is required.")
    @Size(min=4, max=5, message = "Please use 4 to 5 characters")
    @Column(nullable = false, updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is required.")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date start_date;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;


    @PrePersist
    protected void onCreate() {
        created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_At = new Date();
    }

}
