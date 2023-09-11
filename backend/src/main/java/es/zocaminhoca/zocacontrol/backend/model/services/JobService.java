package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.JobDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Job;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    public List<Job> getJobs() {

        List<Job> jobs =
                StreamSupport.stream(jobDao.findAll().spliterator(), false).collect(Collectors.toList());
        return jobs;

    }

    public Job markJobAsDone(long jobId) throws InstanceNotFoundException {

        Optional<Job> opJob = jobDao.findById(jobId);
        if (opJob.isEmpty()) {
            throw new InstanceNotFoundException(Job.class.getSimpleName(), String.valueOf(jobId));
        }
        Job job = opJob.get();
        job.setDoneDate(LocalDate.now());
        job.setWeekOfYearDone(WeekOfYearOperations.getWeekOfYear(job.getDoneDate()));

        jobDao.save(job);
        return job;
    }
}
