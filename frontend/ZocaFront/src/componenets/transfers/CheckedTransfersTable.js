import React, {useEffect, useState} from "react";
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, TextField, TablePagination} from '@mui/material'
import tableStyles from '../../styles/Tables.module.css'
import styles from '../../styles/CheckedTransfersTable.module.css';
import { getCheckedTransfers, writeCheckedTransfers } from "../../backend/TransfersService";
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import { Close } from "@mui/icons-material";
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import { markJobAsDone } from "../../backend/JobService";

const CheckedTransfersTable = ({transfersFilename, setSuccessAlert, setErrorAlert, jobId}) => {

    const [checkedTransfers, setCheckedTransfers] = useState([]);
    const [page, setPage] = useState(0);
    const [ascendingDateOrder, setAscendingDateOrder] = useState(false);
    const [filename, setFilename] = useState('');

    const transfersPerPage = parseInt(process.env.NEXT_PUBLIC_TRANSFERS_PER_PAGE);
    const startIndex = page * transfersPerPage;
    const endIndex = startIndex + transfersPerPage;
    const showTransferes = checkedTransfers.slice(startIndex, endIndex);

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }

    const handleWriteCheckedTransfers = () => {
        const date = new Date();
        const dateStr = String(date.getDate()).padStart(2,'0') + "-" + String(date.getMonth() + 1).padStart(2,'0') + "-" + date.getFullYear();
        const sendFilename = filename ? filename.trim() : ("transferencias" + dateStr);

        writeCheckedTransfers(sendFilename, checkedTransfers, (pathDto) => {
            setSuccessAlert("Pedidos gardados en: "+pathDto.path);
            markJobAsDone(jobId);
            setCheckedTransfers([]);
            setFilename('');
        }, (error)=>{setErrorAlert(error.message)})
    }

    const handleChangeDateOrder = () => {
        if(ascendingDateOrder) {
            setCheckedTransfers(checkedTransfers.sort(compareDatesDescending));
        } else {
            setCheckedTransfers(checkedTransfers.sort(compareDatesAscending));
        }
        setAscendingDateOrder(!ascendingDateOrder);
    }

    const compareDatesAscending = (dateStringA, dateStringB) => {
        const datePartsA = dateStringA.operationDate.split('-');
        const datePartsB = dateStringB.operationDate.split('-');
        const dateA = new Date(+datePartsA[2], +datePartsA[1]-1, +datePartsA[0])
        const dateB = new Date(+datePartsB[2], +datePartsB[1]-1, +datePartsB[0])

        return dateA - dateB;
    }

    const compareDatesDescending = (dateStringA, dateStringB) => {
        const datePartsA = dateStringA.operationDate.split('-');
        const datePartsB = dateStringB.operationDate.split('-');
        const dateA = new Date(+datePartsA[2], +datePartsA[1]-1, +datePartsA[0])
        const dateB = new Date(+datePartsB[2], +datePartsB[1]-1, +datePartsB[0])

        return dateB - dateA;
    }

    useEffect(() => {
        getCheckedTransfers(transfersFilename, (response)=>{setCheckedTransfers(response.sort(compareDatesDescending))}, ()=>{})
    },[]);

    return (
        <div>
            <div className={tableStyles.tableContainer}>
                <TableContainer className={tableStyles.table} component={Paper}> 
                    <Table > 
                        <TableHead>
                            <TableRow>
                                <TableCell>
                                    <div className={styles.clickableTableHeaderCell}  onClick={handleChangeDateOrder}>
                                        Data Operación
                                        {ascendingDateOrder ? <ArrowUpwardIcon className={styles.orderIcon}/> : <ArrowDownwardIcon className={styles.orderIcon}/>}
                                    </div>
                                </TableCell>
                                <TableCell>Data Valor</TableCell>
                                <TableCell>Concepto</TableCell>
                                <TableCell>Importe</TableCell>
                                <TableCell>Nombre</TableCell>
                                <TableCell>Email</TableCell>
                                <TableCell>Confirmado</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {showTransferes.map((row, index) => (
                                <TableRow key={index}>
                                    <TableCell>{row.operationDate}</TableCell>
                                    <TableCell>{row.valueDate}</TableCell>
                                    <TableCell>{row.concept}</TableCell>
                                    <TableCell>{row.amount}</TableCell>
                                    <TableCell>{row.clientDto ? row.clientDto.name : ''}</TableCell>
                                    <TableCell>{row.clientDto ? row.clientDto.email : ''}</TableCell>
                                    <TableCell>{row.checked ? <CheckIcon className={styles.checkIcon}/> : <Close className={styles.crossIcon}/>}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    className={tableStyles.pagination}
                    component="div"
                    count={checkedTransfers.length}
                    rowsPerPageOptions={[transfersPerPage]}
                    rowsPerPage={transfersPerPage}
                    page={page}
                    onPageChange={handlePageChange}
                />
            </div>
            <div>
                <div className={styles.saveButtonContainer}>
                    <input
                        className={styles.filenameInput}
                        type="text"
                        placeholder="Nome do arquivo"
                        value={filename}
                        onChange={(e) => setFilename(e.target.value) }/>
                </div>
                <div className={styles.saveButtonContainer}>
                    <button onClick={handleWriteCheckedTransfers} className={styles.saveButton}>Gardar arquivo de transferencias</button>
                </div>
            </div>
        </div>
    )

}

export default CheckedTransfersTable;
