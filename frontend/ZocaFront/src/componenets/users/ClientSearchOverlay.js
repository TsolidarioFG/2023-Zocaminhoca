import React, {useState, useEffect} from 'react';
import { searchClientByName } from '../../backend/UserService';
import styles from '../../styles/Overlay.module.css'

const ClientSearchOverlay = ({userId, setClient, closeModal}) => {

    const [searchName, setSearchName] = useState('');
    const [clientList, setClientList] = useState([]);
    const [selectedCLient, setSelectedClient] = useState({});

    const selectClient = (client) => {
        setClient(client);
        closeModal();
    }

    const handleOutsideClick = (event) => {
            if(event.target.classList.contains('overlayContainer')) {
                closeModal();
            }
    }

    useEffect(() => {
        if(searchName.trim().length <= 2) {
            setClientList([]);
            return;
        }

        searchClientByName (searchName, (clients)=> setClientList(clients), ()=>{});
    }, [searchName]);

    return (
        <div className={styles.overlayContainer} onClick={(e)=>{e.stopPropagation()}}>
            <div className={styles.overlay}>
                <input
                    type='text'
                    value={searchName}
                    onChange={e => setSearchName(e.target.value)}
                    placeholder='Buscar cliente'
                />
                <div className={styles.namesListContainer}>
                    <ul className={styles.namesList}>
                        {clientList.map(client => (
                            <li key={client.codclient} onClick={() => {selectClient(client)}}>{client.name}</li>
                        ))}
                    </ul>
                </div>
                <button className={styles.cancelButton} onClick={()=>{closeModal()}}>Cancelar</button>
            </div>
        </div>
    )
}

export default ClientSearchOverlay;