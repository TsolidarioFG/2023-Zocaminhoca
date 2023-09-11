import React, {useState} from "react";
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead } from "@mui/material";
import tableStyles from '../../styles/Tables.module.css'


const OrderLinesTable = ({orderLines}) => {

    return (
        <TableContainer className={tableStyles.table} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Produto</TableCell>
                        <TableCell>Prezo Unidade</TableCell>
                        <TableCell>Cantidade</TableCell>
                        <TableCell>Prezo Total</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {orderLines.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>{row.item}</TableCell>
                            <TableCell>{row.unitPrice}</TableCell>
                            <TableCell>{row.units}</TableCell>
                            <TableCell>{row.units * row.unitPrice}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default OrderLinesTable;