import React, {useState, useEffect} from 'react';
import { findDistributorByName } from '../../backend/DistributionService';
import styles from '../../styles/Overlay.module.css'

const DistributorSearchOverlay = ({addDistributor, closeModal}) => {

    const [searchName, setSearchName] = useState('');
    const [distributorList, setDistributorList] = useState([]);

    const selectDistributor = (distributor) => {
        addDistributor(distributor);
        closeModal();
    }

    useEffect(() => {
        if(searchName.trim().length <= 2) {
            setDistributorList([]);
            return;
        }

        findDistributorByName(searchName, (distributors)=>setDistributorList(distributors), ()=>{});
    },  [searchName]);

    return (
        <div className={styles.overlayContainer} onClick={(e)=>{e.stopPropagation()}}>
            <div className={styles.overlay}>
                <input
                    type='text'
                    value={searchName}
                    onChange={e => setSearchName(e.target.value)}
                    placeholder='Buscar produtora'
                />
                <div className={styles.namesListContainer}>
                    <ul className={styles.namesList}>
                        {distributorList.map(distributor => (
                            <li key={distributor.id} onClick={() => {selectDistributor(distributor)}}>{distributor.name}</li>
                        ))}
                    </ul>
                </div>
                <button className={styles.cancelButton} onClick={()=>{closeModal()}}>Cancelar</button>
            </div>
        </div>
    )
}

export default DistributorSearchOverlay;