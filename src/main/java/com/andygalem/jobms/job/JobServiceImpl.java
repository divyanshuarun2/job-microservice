package com.andygalem.jobms.job;

import com.andygalem.jobms.job.dto.JobDTO;
import com.andygalem.jobms.job.external.Company;
import com.andygalem.jobms.job.external.Review;
import com.andygalem.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl  implements JobService{
    @Autowired
private JobRepository jobRepository;
    @Autowired
    RestTemplate restTemplate;
    @Override
    public List<JobDTO> findAll() {
        //adding DTO with job request

        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDTOS=new ArrayList<>();

//        RestTemplate restTemplate = new RestTemplate();
        for(Job job:jobs){

            jobWithCompanyDTOS.add(converToDto(job));

        }
        return jobWithCompanyDTOS;
    }

    private JobDTO converToDto(Job job) {
        Company company= restTemplate.
                getForObject(
                        "http://COMPANYMS:8083/companies/"+job.getCompanyId(),
                        Company.class);

        ResponseEntity<List<Review>> reviewResponse =
                restTemplate.exchange(
                        "http://REVIEWMS:8084/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });
         List<Review> reviews = reviewResponse.getBody();

          JobDTO jobDTO= JobMapper.jobMapper(job,company,reviews);
          return jobDTO;

    }

    @Override
    public void createJob(Job job) {
    jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        JobDTO jobDto=null;
        if(job!=null){
        jobDto =converToDto(job);
        }
        return jobDto;
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
