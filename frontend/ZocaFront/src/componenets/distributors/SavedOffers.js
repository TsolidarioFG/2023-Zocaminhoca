import React, {useEffect, useState} from 'react';
//import styles from '../../styles/WeeklyOffers.module.css';
import tableStyles from '../../styles/Tables.module.css';
import styles from '../../styles/WeeklyOffers.module.css';
import DeleteIcon from '@mui/icons-material/Delete'
import { addDistributorOffers, getSavedOffers, getWeeklyOffers } from '../../backend/DistributionService';
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton } from "@mui/material";
import OfferLinesTable from './OfferLinesTable';
import ProductTotalsTable from './ProductTotalsTable';
import AddIcon from '@mui/icons-material/Add';
import DistributorSearchOverlay from './DistributorSearchOverlay';
import { markJobAsDone } from '../../backend/JobService';
import SavedOfferLinesTable from './SavedOfferLinesTable';


const SavedOffers = ({}) => {

    const [distributors, setDistributors] = useState([]);
    const [selectedDistributor, setSelectedDistributor] = useState(null);
    const [page, setPage] = useState(0);
    const [offersView, setOffersView] = useState(true);

    const distributorsPerPage = 10;
    const startIndex = page * distributorsPerPage;
    const endIndex = startIndex + distributorsPerPage;
    const showDistributors = distributors.slice(startIndex, endIndex);

    const handleOfferSelection = (distributor) => {
        setSelectedDistributor(distributor);
    }

    const handleSelectOffersView = () => {
        setOffersView(true);
    }
    
    const handleSelectProductsView = () => {
        setOffersView(false);
    }

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }

    useEffect(() => {
        getSavedOffers((response)=>{setDistributors(response)}, ()=>{});
    },[]);

    return(
        <div>
            <div className={styles.viewSelectorContainer}>
                <button className={`${styles.viewSelectionButton} ${offersView ? styles.viewSelectionButtonSelected : ''}`} onClick={handleSelectOffersView}>Ofertas</button>
                <button className={`${styles.viewSelectionButton} ${!offersView ? styles.viewSelectionButtonSelected : ''}`} onClick={handleSelectProductsView}>Total Produtos</button>
            </div>
            {offersView && <div className={tableStyles.tablesContainerGrid}>
                <div className={tableStyles.masterDetailTableContainers}>
                    <div className={tableStyles.tableTitle}>
                        PRODUTORAS
                    </div>
                    <div className={tableStyles.tableContainer}>
                        <TableContainer className={tableStyles.table} component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Nome</TableCell>
                                        <TableCell>Telefono</TableCell>
                                        <TableCell>Email</TableCell>
                                        <TableCell>Nº produtos</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {showDistributors.map((row) => (
                                        <TableRow className={tableStyles.selectableRow} key={row.id} onClick={() => handleOfferSelection(row)}>
                                            <TableCell>{row.name}</TableCell>
                                            <TableCell>{row.phone}</TableCell>
                                            <TableCell>{row.email}</TableCell>
                                            <TableCell>{row.offerDto && row.offerDto.offerLines ? row.offerDto.offerLines.length : 0}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <TablePagination
                            className={tableStyles.pagination}
                            component="div"
                            count={distributors.length}
                            rowsPerPageOptions={[distributorsPerPage]}
                            rowsPerPage={distributorsPerPage}
                            page={page}
                            onPageChange={handlePageChange}
                        />
                    </div>
                </div>
                <div className={tableStyles.masterDetailTableContainers}>
                    <div className={tableStyles.tableTitle}>
                        OFERTA
                    </div>
                    <div className={tableStyles.tableContainer}>
                        {selectedDistributor && <SavedOfferLinesTable distributors={selectedDistributor}/>}
                    </div>
                </div>

            </div>}
            {!offersView && <ProductTotalsTable distributors={distributors}/>}
        </div>
    )

}

export default SavedOffers;