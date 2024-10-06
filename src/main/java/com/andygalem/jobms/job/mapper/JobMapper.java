package com.andygalem.jobms.job.mapper;

import com.andygalem.jobms.job.Job;
import com.andygalem.jobms.job.dto.JobDTO;

import com.andygalem.jobms.job.external.Company;
import com.andygalem.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO jobMapper(Job job,
                                              Company company, List<Review> reviews){
        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;

    }
}
