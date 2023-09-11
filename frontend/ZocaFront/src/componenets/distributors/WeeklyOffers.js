import React, {useEffect, useState} from 'react';
//import styles from '../../styles/WeeklyOffers.module.css';
import tableStyles from '../../styles/Tables.module.css';
import styles from '../../styles/WeeklyOffers.module.css';
import DeleteIcon from '@mui/icons-material/Delete'
import { addDistributorOffers, getWeeklyOffers } from '../../backend/DistributionService';
import { TableContainer, Table, TableBody, TableRow, TableCell, TablePagination, Paper, TableHead, IconButton } from "@mui/material";
import OfferLinesTable from './OfferLinesTable';
import ProductTotalsTable from './ProductTotalsTable';
import AddIcon from '@mui/icons-material/Add';
import DistributorSearchOverlay from './DistributorSearchOverlay';
import { markJobAsDone } from '../../backend/JobService';


const WeeklyOffers = ({setSuccessMessage, jobId}) => {

    const [distributors, setDistributors] = useState([]);
    const [selectedDistributor, setSelectedDistributor] = useState(null);
    const [page, setPage] = useState(0);
    const [offersView, setOffersView] = useState(true);
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedOrderIdx, setSelectedOrderIdx] = useState(null);


    const distributorsPerPage = 10;
    const startIndex = page * distributorsPerPage;
    const endIndex = startIndex + distributorsPerPage;
    const showDistributors = distributors.slice(startIndex, endIndex);

    const openModal = () => {
        setModalOpen(true);
    }

    const closeModal = () => {
        setModalOpen(false);
    }

    const handleAddDistributor = (distributor) => {
        setDistributors((oldDistributors) => [...oldDistributors, distributor])
    }

    const handleDeleteDistributor = (distributorId) => {
        if(selectedDistributor && (distributorId === selectedDistributor.id)) {
            setSelectedDistributor(null)
        }
        setDistributors((oldDistributors) => oldDistributors.filter((distributor) => distributor.id !== distributorId));
    }

    const handleSuccessSave = () => {
        setSuccessMessage("Ofertas gardadas con éxito")
        markJobAsDone(jobId,()=>{},()=>{});
        setSelectedDistributor(null);

    }

    const handleSaveOffers = () => {
        addDistributorOffers(distributors, handleSuccessSave, () => {});
    }

    const handleOfferSelection = (distributor, idx) => {
        setSelectedDistributor(distributor);
        setSelectedOrderIdx(idx);
    }

    const handleSelectOffersView = () => {
        setOffersView(true);
    }
    
    const handleSelectProductsView = () => {
        setOffersView(false);
    }

    const handleUpdateOffer = (distributor) => {
     
        const distributorIndex = distributors.findIndex(d => d.id === distributor.id);

        const updatedDistributors = [
            ...distributors.slice(0, distributorIndex),
            distributor,
            ...distributors.slice(distributorIndex + 1),
        ]

        setDistributors(updatedDistributors);
        setSelectedDistributor(distributor);
    }

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    }



    useEffect(() => {
        getWeeklyOffers((response)=>{setDistributors(response)}, ()=>{});
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
                                        <TableCell>Acción</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {showDistributors.map((row,index) => (
                                        <TableRow className={`${tableStyles.selectableRow} ${index===selectedOrderIdx ? tableStyles.selectedRow : ''}`} key={row.id} onClick={() => handleOfferSelection(row,index)}>
                                            <TableCell>{row.name}</TableCell>
                                            <TableCell>{row.phone}</TableCell>
                                            <TableCell>{row.email}</TableCell>
                                            <TableCell>{row.offerDto && row.offerDto.offerLines ? row.offerDto.offerLines.length : 0}</TableCell>
                                            <TableCell>
                                                <IconButton
                                                    onClick={(event) => {
                                                        event.stopPropagation();
                                                        handleDeleteDistributor(row.id);
                                                    }}
                                                >
                                                    <DeleteIcon/>
                                                    </IconButton>
                                            </TableCell>
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
                        <div className={styles.addButtonContainer}>
                            <button className={styles.addDistributorButton} onClick={openModal}>
                                <AddIcon/>
                            </button>
                        </div>
                    </div>
                </div>
                <div className={tableStyles.masterDetailTableContainers}>
                    <div className={tableStyles.tableTitle}>
                        OFERTA
                    </div>
                    <div className={tableStyles.tableContainer}>
                        {selectedDistributor && <OfferLinesTable distributor={selectedDistributor} updateOffers={handleUpdateOffer}/>}
                    </div>
                </div>

            </div>}
            {!offersView && <ProductTotalsTable distributors={distributors}/>}
            {modalOpen && <div>
                <DistributorSearchOverlay addDistributor={(distributor) => handleAddDistributor(distributor)}
                    closeModal={closeModal}/>
            </div>}
            {offersView && <div className={styles.saveButtonContainer}>
                <button onClick={handleSaveOffers} className={styles.saveButton}>Guardar</button>
            </div>}
        </div>
    )

}

export default WeeklyOffers;