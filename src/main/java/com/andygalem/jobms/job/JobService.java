package com.andygalem.jobms.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    void createJob(Job job);

    Job getJobById(Long id);

    boolean deleteById(Long id);

    boolean updatebyId(Long id, Job updatedJob);
}
