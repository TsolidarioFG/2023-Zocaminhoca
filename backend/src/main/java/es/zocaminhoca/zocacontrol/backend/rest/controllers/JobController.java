package es.zocaminhoca.zocacontrol.backend.rest.controllers;

import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.services.JobService;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.ErrorsDto;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.JobConversor;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.JobDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundExcepiton(InstanceNotFoundException exception,
                                                     Locale locale) {

        String errorMessage =
                "No se encuentra el " + exception.getInstanceClass() + " con id " + exception.getKey();

        return new ErrorsDto(errorMessage, exception.getKey(), exception.getInstanceClass());
    }

    @Operation(
            summary = "Devuelve la lista de todas las tareas almacenadas"
    )
    @GetMapping("")
    public List<JobDto> getJobs() {
        return JobConversor.toJobDtos(jobService.getJobs());
    }

    @Operation(
            summary = "Marca una tarea como realizada en la fecha actual"
    )
    @PostMapping("/{jobId}")
    public JobDto markJobAsDone(@PathVariable long jobId) throws InstanceNotFoundException {

        return JobConversor.toJobDto(jobService.markJobAsDone(jobId));

    }
}
