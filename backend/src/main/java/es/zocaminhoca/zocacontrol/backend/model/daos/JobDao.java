package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobDao extends CrudRepository<Job, Long> {
}
