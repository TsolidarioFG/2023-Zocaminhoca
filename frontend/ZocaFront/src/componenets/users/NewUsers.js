import React,{useState, useEffect} from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, TextField} from '@mui/material'
import {Edit as EditIcon, Save as SaveIcon, Delete as DeleteIcon} from '@mui/icons-material'
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';
import styles from '../../styles/NewUsers.module.css';
import tableStyles from '../../styles/Tables.module.css'
import {getNewUsers, updateUser} from '../../backend/UserService';
import ClientSearchOverlay from './ClientSearchOverlay';
import { markJobAsDone } from '../../backend/JobService';

const NewUsers = ({jobId}) => {

    const [users, setUsers] = useState([]);
    const [updatedData, setUpdatedData] = useState({});
    const [selectedId, setSelectedId] = useState(null);
    const [selectedClient, setSelectedClient] = useState({});
    const [modalOpen, setModalOpen] = useState(false);
    const [clientList, setClientList] = useState({});

    const openModal = () => {
        setModalOpen(true);
    }

    const closeModal = () => {
        setModalOpen(false);
    }

    const handleEdit = (id) => {
        setSelectedClient({});
        setSelectedId(id);
        setUpdatedData(users.find((user) => user.id === id));
    }

    const handleSave = () => {

        let updatedUserIdx = users.findIndex((user) => user.id === selectedId);
        let usersCopy = [...users];
        let updatedUser = {
            ...updatedData
        };
        usersCopy[updatedUserIdx] = updatedUser;
        setUsers(usersCopy);

        updateUser(updatedUser, (user)=>{
            markJobAsDone(jobId,()=>{},()=>{})
        }, ()=>{});

        setSelectedId(null);
        setUpdatedData({});
    }

    const selectClient = (client) => {

        setSelectedClient(client);
        if(client.email) {
            setUpdatedData({...updatedData, clientId: client.codclient, webEmail:client.email})
        } else {
            setUpdatedData({ ...updatedData, clientId: client.codclient })
        }
    }

    const handleCancel = () => {}
    
    useEffect(() => {
        getNewUsers((users) => {setUsers(users)}, ()=>{});

    },[]);

    return (
        <div className={tableStyles.tableContainer}>
        <TableContainer className={tableStyles.table} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow >
                        <TableCell>ID</TableCell>
                        <TableCell>IDTelegram</TableCell>
                        <TableCell>ID-Web</TableCell>
                        <TableCell>ID-Cliente</TableCell>
                        <TableCell>Nome</TableCell>
                        <TableCell>Apelidos</TableCell>
                        <TableCell>Nome Usuaria</TableCell>
                        <TableCell>Email Web</TableCell>
                        <TableCell>Accions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((row => (
                        <TableRow key={row.id}>
                            <TableCell>{row.id}</TableCell>
                            <TableCell>{row.telegramId}</TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <TextField
                                    placeholder={"Id Web"}
                                    value={updatedData.webId || ''}
                                    onChange={(e) => setUpdatedData({ ...updatedData, webId: e.target.value })}
                                />
                                ) : (
                                row.webId
                                )}</TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                    updatedData.clientId ? 
                                        updatedData.clientId : 
                                        <button className={styles.selectClientButton} onClick={openModal}>
                                            Seleccionar cliente
                                        </button>
                                ) : (
                                row.clientId
                                )}</TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <TextField
                                    placeholder={"Nome"}
                                    value={updatedData.firstName || ''}
                                    onChange={(e) => setUpdatedData({ ...updatedData, firstName: e.target.value })}
                                />
                                ) : (
                                row.firstName
                                )}
                            </TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <TextField
                                    placeholder={"Apelidos"}
                                    value={updatedData.lastName || ''}
                                    onChange={(e) => setUpdatedData({ ...updatedData, lastName: e.target.value })}
                                />
                                ) : (
                                row.lastName
                                )}
                            </TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <TextField
                                    placeholder={"Nome usuaria"}
                                    value={updatedData.userName || ''}
                                    onChange={(e) => setUpdatedData({ ...updatedData, userName: e.target.value })}
                                />
                                ) : (
                                row.userName
                                )}
                            </TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <TextField
                                    placeholder={"Email"}
                                    value={updatedData.webEmail || ''}
                                    onChange={(e) => setUpdatedData({ ...updatedData, webEmail: e.target.value })}
                                />
                                ) : (
                                row.webEmail
                                )}
                            </TableCell>
                            <TableCell>
                                {selectedId === row.id ? (
                                <>
                                    <IconButton onClick={handleSave}>
                                    <SaveIcon />
                                    </IconButton>
                                    <IconButton onClick={handleCancel}>
                                    <CancelOutlinedIcon/>
                                    </IconButton>
                                </>
                                ) : (
                                <IconButton onClick={() => handleEdit(row.id)}>
                                    <EditIcon />
                                </IconButton>
                                )}
                            </TableCell>
                        </TableRow>
                    )))}
                </TableBody>
            </Table>
        </TableContainer>
        {modalOpen && <div>
                <ClientSearchOverlay setClient={selectClient}
                    closeModal={closeModal}/>
            </div>}
        </div>
    )
}

export default NewUsers;