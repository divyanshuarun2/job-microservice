package com.andygalem.jobms.job;

import com.andygalem.jobms.job.dto.JobWithCompanyDTO;
import com.andygalem.jobms.job.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl  implements JobService{
    @Autowired
private JobRepository jobRepository;
    @Override
    public List<JobWithCompanyDTO> findAll() {
        //adding DTO with job request

        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS=new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        for(Job job:jobs){
            JobWithCompanyDTO jobWithCompanyDTO= new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);

            Company company = restTemplate.getForObject(
                    "http://localhost:8083/companies/"+job.getCompanyId(),
                    Company.class);
            jobWithCompanyDTO.setCompany(company);
            jobWithCompanyDTOS.add(jobWithCompanyDTO);

        }
        return jobWithCompanyDTOS;
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
