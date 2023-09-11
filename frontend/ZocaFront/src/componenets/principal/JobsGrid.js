import React, {useEffect} from "react";
import JobCard from "./JobCard";
import styles from '../../styles/JobsGrid.module.css'
import { getAllJobs } from "../../backend/JobService";

const JobsGrid = ({jobs, setJob, setJobs}) => {


    useEffect(() => {
        getAllJobs((jobs) => {
          setJobs(jobs);
        }, ()=>{})
      },[]);


    return (
        <div className={styles.jobsGrid}>
            {jobs && jobs.map((job) => (
                <JobCard key={job.id} job={job} setJob={setJob}/>
            ))}
        </div>
    )
}

export default JobsGrid;