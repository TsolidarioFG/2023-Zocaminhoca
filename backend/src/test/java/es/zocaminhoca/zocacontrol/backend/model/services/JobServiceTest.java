package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.JobDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Job;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class JobServiceTest {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobService jobService;

    private Job createJob() {
        Job job = new Job();
        job.setName("testJob");
        job.setDescription("testJob");
        job.setWeekOfYearDone(1);
        job.setDoneDate(LocalDate.now());
        jobDao.save(job);
        return job;
    }

    @Test
    public void getJobsTest() {
        Job job = createJob();

        List<Job> jobs = jobService.getJobs();

        assertTrue(jobs.size() == 1);
    }

    @Test
    public void markJobAsDoneTest() throws InstanceNotFoundException {
        Job job = createJob();
        jobService.markJobAsDone(job.getId());

        Job foundJob = jobDao.findById(job.getId()).get();

        assertTrue(foundJob.getDoneDate().isEqual(LocalDate.now()));
    }

    @Test
    public void markJobAsDoneJobNotFoundTest() throws InstanceNotFoundException {

        assertThrows(InstanceNotFoundException.class, () -> {
            jobService.markJobAsDone(Long.valueOf(1));
        });
    }

}
