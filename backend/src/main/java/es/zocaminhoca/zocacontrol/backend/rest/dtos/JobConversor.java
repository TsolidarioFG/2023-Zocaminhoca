package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Job;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JobConversor {

    public final static JobDto toJobDto(Job job) {

        boolean jobDone =
                (job.getWeekOfYearDone() == WeekOfYearOperations.getWeekOfYear(LocalDate.now()));
        return new JobDto(job.getId(), job.getName(), job.getDescription(), job.getDoneDate(),
                jobDone);

    }

    public final static List<JobDto> toJobDtos(List<Job> jobs) {

        return jobs.stream().map(j -> toJobDto(j)).collect(Collectors.toList());

    }
}
