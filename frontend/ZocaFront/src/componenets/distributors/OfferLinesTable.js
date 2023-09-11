import React, { useState } from "react";
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton, TextField } from "@mui/material";
import styles from '../../styles/OfferLinesTable.module.css'
import tableStyles from '../../styles/Tables.module.css'
import DeleteIcon from '@mui/icons-material/Delete'
import AddOutlinedIcon from '@mui/icons-material/AddOutlined';

const OfferLinesTable = ({distributor, updateOffers}) => {

    const [newOfferLine, setNewOfferLine] = useState({
        productCode:'',
        product:'',
        quantity:0,
        price: 0.0
    });

    const handleAddOfferLine = () => {

        const updatedOfferLine = {
            productCode: newOfferLine.productCode,
            product: newOfferLine.product,
            quantity: newOfferLine.quantity,
            price: newOfferLine.price
        };

        const updatedDistributor = {
            ...distributor,
                offerDto: {
                    ...distributor.offerDto,
                    offerLines: [...distributor.offerDto.offerLines, updatedOfferLine]
                }
        }
        updateOffers(updatedDistributor);
        setNewOfferLine({
            productCode:'',
            product:'',
            quantity:0,
            price: 0.0
        });
    }

    const handleDeleteOfferLine = (offerIndex) => {

        const updatedDistributor = {
            ...distributor,
            offerDto: {
                ...distributor.offerDto,
                offerLines: [
                    ...distributor.offerDto.offerLines.slice(0, offerIndex),
                    ...distributor.offerDto.offerLines.slice(offerIndex + 1)
                ]
            }
        }
        updateOffers(updatedDistributor);
    }

    const handleEditFieldChange = (index, field, value) => {
        const updateOfferLine = {
            ...distributor.offerDto.offerLines[index],
            [field]: value
        };
        const updatedOfferLines = distributor.offerDto.offerLines.map((offerLine, i) => {
            return i === index ? updateOfferLine : offerLine;
        });

        updateOffers({
            ...distributor,
            offerDto: {
                ...distributor.offerDto,
                offerLines: updatedOfferLines
            }
        });
    }

    return (
        <TableContainer className={tableStyles.table} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Código</TableCell>
                        <TableCell>Produto</TableCell>
                        <TableCell>Cantidad</TableCell>
                        <TableCell>Prezo por unidade</TableCell>
                        <TableCell>Acción</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {distributor.offerDto.offerLines.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>{row.productCode}</TableCell>
                            <TableCell>{row.product}</TableCell>
                            {/*<TableCell>{row.quantity}</TableCell>*/}
                            <TableCell>
                                <input
                                    className={tableStyles.tableInput}
                                    type="number"
                                    value={row.quantity}
                                    onChange={(event) => {
                                        handleEditFieldChange(index, "quantity", event.target.value);
                                    }}
                                />
                            </TableCell>
                            {/*<TableCell>{row.price}</TableCell>*/}
                            <TableCell>
                                <input
                                    className={tableStyles.tableInput}
                                    type="number"
                                    value={row.price}
                                    onChange={(event) => {
                                        handleEditFieldChange(index, "price", event.target.value);
                                    }}
                                />
                            </TableCell>
                            <TableCell>
                                <IconButton
                                    onClick={(event) => {
                                        event.stopPropagation();
                                        handleDeleteOfferLine(index)
                                    }}
                                >
                                    <DeleteIcon/>
                                </IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                    <TableRow>
                    <TableCell>
                            <TextField
                                placeholder="Código"
                                value={newOfferLine.productCode || ''}
                                onChange={(e) => setNewOfferLine({...newOfferLine, productCode:e.target.value})}
                            />
                        </TableCell>
                        <TableCell>
                            <TextField
                                placeholder="Produto"
                                value={newOfferLine.product || ''}
                                onChange={(e) => setNewOfferLine({...newOfferLine, product:e.target.value})}
                            />
                        </TableCell>
                        <TableCell>
                            <TextField
                                type="number"
                                placeholder="Cantidade"
                                value={newOfferLine.quantity || ''}
                                onChange={(e) => setNewOfferLine({...newOfferLine, quantity:e.target.value})}
                            />
                        </TableCell>
                        <TableCell>
                            <TextField
                                type="number"
                                step="0.01"
                                placeholder="Prezo"
                                value={newOfferLine.price || ''}
                                onChange={(e) => setNewOfferLine({...newOfferLine, price:e.target.value})}
                            />
                        </TableCell>
                        <TableCell>
                            <IconButton onClick={handleAddOfferLine}>
                                <AddOutlinedIcon/>
                            </IconButton>
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default OfferLinesTable;