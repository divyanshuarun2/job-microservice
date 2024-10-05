package com.andygalem.jobms.job;

import com.andygalem.jobms.job.dto.JobWithCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping("")
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){

       return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job!=null){
            return new ResponseEntity<>(job, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @PostMapping("")
    public ResponseEntity<String> createJob(@RequestBody Job job){
       jobService.createJob(job);
      return new ResponseEntity<>("Job created",HttpStatus.CREATED);

    }
   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        boolean isdeleted= jobService.deleteById(id);
        if(isdeleted){
            return ResponseEntity.ok("Job with id:"+id+" is Deleted");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateById(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean isUpdated= jobService.updatebyId(id,updatedJob);
        if(isUpdated){
            return ResponseEntity.ok("Job with id:"+id+" is updated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
