import React, {useEffect, useState} from 'react';
import tableStyles from '../../styles/Tables.module.css';
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton } from "@mui/material";


const ProductTotalsTable = ({distributors}) => {

    const [productsTotal, setProductsTotal] = useState([]);
    const [page, setPage] = useState(0);

    const productTotalsPerPAge = 15;
    const startIndex = page * productTotalsPerPAge;
    const endIndex = startIndex + productTotalsPerPAge;
    const showProductsTotal = productsTotal.slice(startIndex, endIndex)

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }

    const generateProductsTotals = (distributorList) => {
        const prodTotals = {};

        distributorList.forEach((distributor) => {
            distributor.offerDto.offerLines.forEach((offerLine => {
                const {productCode, product, quantity} = offerLine;
                const intQuantity = parseInt(quantity,10);
                if(!prodTotals[productCode]) {
                    prodTotals[productCode] = {
                        productCode,
                        product,
                        quantity:0
                    };
                }
                prodTotals[productCode].quantity += intQuantity;
            }));
        });

        setProductsTotal(Object.values(prodTotals));
    }

    useEffect(() => {
        generateProductsTotals(distributors);
    },[])

    return(
        <div className={tableStyles.TableContainer}>
            <TableContainer className={tableStyles.table} component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Código</TableCell>
                            <TableCell>Produto</TableCell>
                            <TableCell>Cantidade</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {showProductsTotal.map((row, index) => (
                            <TableRow key={index}>
                                <TableCell>{row.productCode}</TableCell>
                                <TableCell>{row.product}</TableCell>
                                <TableCell>{row.quantity}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                className={tableStyles.pagination}
                component="div"
                count={productsTotal.length}
                rowsPerPageOptions={[productTotalsPerPAge]}
                rowsPerPage={productTotalsPerPAge}
                page={page}
                onPageChange={handlePageChange}
            />
        </div>
    )

}

export default ProductTotalsTable;