import React, { useState } from "react";
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton, TextField } from "@mui/material";
import styles from '../../styles/OfferLinesTable.module.css'
import tableStyles from '../../styles/Tables.module.css'
import DeleteIcon from '@mui/icons-material/Delete'
import AddOutlinedIcon from '@mui/icons-material/AddOutlined';

const SavedOfferLinesTable = ({distributors}) => {

    return (
        <TableContainer className={tableStyles.table} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Código</TableCell>
                        <TableCell>Produto</TableCell>
                        <TableCell>Cantidad</TableCell>
                        <TableCell>Prezo por unidade</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {distributors.offerDto.offerLines.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>{row.productCode}</TableCell>
                            <TableCell>{row.product}</TableCell>
                            <TableCell>{row.quantity}</TableCell>
                            <TableCell>{row.price}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default SavedOfferLinesTable;