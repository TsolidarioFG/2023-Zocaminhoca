import Head from 'next/head';
import styles from '../styles/Home.module.css';
import LeftSideBar from '../componenets/LeftSideBar';
import React, {useEffect, useState} from "react";
import HeaderWelcome from '../componenets/HeaderWelcome';
import NewUsers from '../componenets/users/NewUsers';
import OrdersJob from '../componenets/pedidos/OrdersJob';
import AddDistributor from '../componenets/distributors/AddDistributor';
import WeeklyOffers from '../componenets/distributors/WeeklyOffers';
import CheckTransfersJob from '../componenets/transfers/CheckTransfersJob';
import ErrorAlert from '../componenets/common/ErrorAlert';
import SuccessAlert from '../componenets/common/SuccessAlert';
import JobsGrid from '../componenets/principal/JobsGrid';
import { getAllJobs } from '../backend/JobService';
import SavedOrdersList from '../componenets/pedidos/SavedOrdersList';
import UnpaiReceiptTable from '../componenets/transfers/UnpaidReceiptTable';
import SavedOffers from '../componenets/distributors/SavedOffers';


export default function Home() {

  const [selectedJob, setSelectedJob] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [jobs, setJobs] = useState([]);

  const handleSelectJob = (jobName)  => {
    setSelectedJob(jobName);
  }

  const getJobId = (jobName) => {
    const foundJob = jobs.find((job) => job.name === jobName);

    if(foundJob) {
      return foundJob.id;
    } else {
      return -1;
    }
    
  }

  const handleCloseError = () => {
    setErrorMessage('');
  }

  const handleCloseSuccess = () => {
    setSuccessMessage('');
  }

  const handleShowSuccessAlert = (message) => {
    setSuccessMessage(message);
  }

  const handleShowErrorAlert = (message) => {
    setErrorMessage(message);
  }

  return (
    <div className = {styles.grid}>
      {/*<div className = {styles.topContainer}>
      </div>*/}
      <div className={styles.leftSideBar}>
        <LeftSideBar onSelectButton={handleSelectJob}/>
      </div>
      <div className={styles.rightContainer}>
        <HeaderWelcome/>
        {selectedJob === '' && <JobsGrid jobs={jobs} setJob={setSelectedJob} setJobs={setJobs}/>}
        {selectedJob === "users" && <NewUsers jobId={getJobId('users')}/>}
        {selectedJob === "pedidos" && <OrdersJob setSuccessAlert={handleShowSuccessAlert} setErrorAlert={handleShowErrorAlert} jobId={getJobId('pedidos')}/>}
        {selectedJob === "newDistributors" && <AddDistributor setSuccessAlert={handleShowSuccessAlert} setErrorAlert={handleShowErrorAlert}/>}
        {selectedJob === "offers" && <WeeklyOffers setSuccessMessage={handleShowSuccessAlert} jobId={getJobId('offers')}/>}
        {selectedJob === "transfers" && <CheckTransfersJob setSuccessAlert={handleShowSuccessAlert} setErrorAlert={handleShowErrorAlert} jobId={getJobId('transfers')}/>}
        {selectedJob === "savedOrders" && <SavedOrdersList/>}
        {selectedJob === "unpaidReceipts" && <UnpaiReceiptTable/>}
        {selectedJob === "savedOffers" && <SavedOffers/>}
      </div>
      {errorMessage && <ErrorAlert message={errorMessage} onClose={handleCloseError}/>}
      {successMessage && <SuccessAlert message={successMessage} onClose={handleCloseSuccess}/>}
    </div>
  )
}
