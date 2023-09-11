import React, {useEffect, useState} from 'react';
import tableStyles from '../../styles/Tables.module.css';
import { getUnpaidReceipts } from '../../backend/TransfersService';
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton } from "@mui/material";


const UnpaiReceiptTable = () => {

    const [unpaidReceipts, setUnpaidReceipts] = useState([]);

    const [page, setPage] = useState(0);
    const receiptsPerPage = 15;
    const startIndex = page * receiptsPerPage;
    const endIndex = startIndex + receiptsPerPage;
    const showReceipts = unpaidReceipts.slice(startIndex, endIndex)

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }

    useEffect(() => {
        getUnpaidReceipts((receipts)=>{
            setUnpaidReceipts(receipts);
        }, ()=>{});
    },[])

    return(
        <div className={tableStyles.TableContainer}>
            <TableContainer className={tableStyles.table} component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Socia</TableCell>
                            <TableCell>Id Recibo</TableCell>
                            <TableCell>Data</TableCell>
                            <TableCell>Prezo</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {showReceipts.map((row, index) => (
                            <TableRow key={index}>
                                <TableCell>{row.clientName}</TableCell>
                                <TableCell>{row.billDto.id}</TableCell>
                                <TableCell>{row.billDto.date}</TableCell>
                                <TableCell>{row.price}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                className={tableStyles.pagination}
                component="div"
                count={unpaidReceipts.length}
                rowsPerPageOptions={[receiptsPerPage]}
                rowsPerPage={receiptsPerPage}
                page={page}
                onPageChange={handlePageChange}
            />
        </div>
    )

}

export default UnpaiReceiptTable;