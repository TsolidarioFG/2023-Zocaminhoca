import React, {useState, useEffect} from "react";
import SelectFile from "../common/SelectFile";
import CheckedTransfersTable from "./CheckedTransfersTable";

const CheckTransfersJob = ({setFilename,setSuccessAlert, setErrorAlert, jobId}) => {

    const [transfersFilename, setTransfersFilename] = useState('');

    const handleSetFilename = (filename) => {
        setTransfersFilename(filename);
    }

    return(
        <>
            {
                transfersFilename === '' ? <SelectFile setFilename={handleSetFilename} selectorText={"Selecciona arquivo de transferencias"}/> 
                : <CheckedTransfersTable setSuccessAlert={setSuccessAlert} setErrorAlert={setErrorAlert} transfersFilename={transfersFilename} jobId={jobId}/>
            }
        </>
    )

}

export default CheckTransfersJob;

