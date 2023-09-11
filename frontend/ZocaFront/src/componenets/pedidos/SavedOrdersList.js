import React, {useEffect, useState} from "react";
import styles from '../../styles/PedidosList.module.css';
import tableStyles from '../../styles/Tables.module.css';
import { getWeekOrders, readPedidos, savePedidos } from "../../backend/OrderService";
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead } from "@mui/material";
import OrderLinesTable from "./OrderLinesTable";
import { markJobAsDone } from "../../backend/JobService";

const SavedOrdersList = ({}) => {

    const [orders, setOrders] = useState([]);
    const [selectedOrderIdx, setSelectedOrderIdx] = useState(null);
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [page, setPage] = useState(0);

    const ordersPerPage = 10;
    const startIndex = page * ordersPerPage;
    const endIndex = startIndex + ordersPerPage;
    const showOrders = orders.slice(startIndex, endIndex);

    const handleOrderSelection = (order, idx) => {
        setSelectedOrder(order.orderLines);
        setSelectedOrderIdx(idx);
    }

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }

    useEffect(() => {
        getWeekOrders((response)=>{setOrders(response)}, ()=>{});
        
    },[]);

    return (
        <div>
            <div className={tableStyles.tablesContainerGrid}>
                <div className={tableStyles.masterDetailTableContainers}>
                    <div className={tableStyles.tableTitle}>
                        PEDIDOS
                    </div>
                    <div className={tableStyles.tableContainer}>
                        <TableContainer className={tableStyles.table} component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>ID-Web</TableCell>
                                        <TableCell>Usuaria</TableCell>
                                        <TableCell>Email</TableCell>
                                        <TableCell>Nome</TableCell>
                                        <TableCell>Apelidos</TableCell>
                                        <TableCell>Nº Prods</TableCell>
                                        <TableCell>Prezo</TableCell>
                                    </TableRow>          
                                </TableHead>
                                <TableBody>
                                    {showOrders.map((row, index) => (
                                        <TableRow className={`${tableStyles.selectableRow} ${index===selectedOrderIdx ? tableStyles.selectedRow : ''}`} key={row.id || index} onClick={() => handleOrderSelection(row,index)}>
                                            <TableCell>{row.webId}</TableCell>
                                            <TableCell>{row.user ? row.user.id : ''}</TableCell>
                                            <TableCell>{row.email}</TableCell>
                                            <TableCell>{row.name}</TableCell>
                                            <TableCell>{row.lastName}</TableCell>
                                            <TableCell>{row.orderLines.length}</TableCell>
                                            <TableCell>{row.totalPrice}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <TablePagination
                            className={tableStyles.pagination}
                            component="div"
                            count={orders.length}
                            rowsPerPageOptions={[ordersPerPage]}
                            rowsPerPage={ordersPerPage}
                            page={page}
                            onPageChange={handlePageChange}
                        />
                    </div>

                </div>
                <div className={tableStyles.masterDetailTableContainers}>
                    <div className={tableStyles.tableTitle}>
                        DETALLES
                    </div>
                    <div className={tableStyles.tableContainer}>
                        {selectedOrder && <OrderLinesTable orderLines={selectedOrder}/>}
                    </div>
                </div>
                
            </div>
        </div>
    )
}

export default SavedOrdersList;