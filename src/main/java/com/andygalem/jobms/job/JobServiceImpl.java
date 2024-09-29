package com.andygalem.jobms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl  implements JobService{
    @Autowired
private JobRepository jobRepository;
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
    jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if(job!=null){
            return job;
        }
        return job;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean jobfound = jobRepository.existsById(id);

        if(jobfound==true){
            jobRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public boolean updatebyId(Long id, Job updatedJob) {
        Job job = jobRepository.findById(id).orElse(null);

            if(job!=null){
                job.setDescription(updatedJob.getDescription());
                job.setLocation(updatedJob.getLocation());
                job.setTitle(updatedJob.getTitle());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                // adding a functionality if we want to put
                // declare a company while updating
                job.setCompanyId(updatedJob.getCompanyId());

                jobRepository.save(job);
                return true;

            }

        return false;
    }
}
