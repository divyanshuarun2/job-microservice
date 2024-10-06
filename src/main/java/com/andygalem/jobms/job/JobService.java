package com.andygalem.jobms.job;

import com.andygalem.jobms.job.dto.JobDTO;


import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteById(Long id);

    boolean updatebyId(Long id, Job updatedJob);
}
