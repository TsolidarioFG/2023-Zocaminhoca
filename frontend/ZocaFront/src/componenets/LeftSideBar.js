import React, {useState} from "react";
import Link from 'next/link';
import styles from '../styles/LeftSideBar.module.css';
import AccessibilityOutlinedIcon from '@mui/icons-material/AccessibilityOutlined';
import FormatListBulletedOutlinedIcon from '@mui/icons-material/FormatListBulletedOutlined';
import LocalShippingOutlinedIcon from '@mui/icons-material/LocalShippingOutlined';
import ReceiptLongOutlinedIcon from '@mui/icons-material/ReceiptLongOutlined';
import AddCardIcon from '@mui/icons-material/AddCard';
import AssignmentTurnedInOutlinedIcon from '@mui/icons-material/AssignmentTurnedInOutlined';
import PlaylistRemoveOutlinedIcon from '@mui/icons-material/PlaylistRemoveOutlined';

const LeftSideBar = ({ onSelectButton }) => {

    const [selectedJob, setSelectedJob] = useState('');

    const handleSelectJob = (jobName) => {
        setSelectedJob(jobName);
        onSelectButton(jobName);
    }

    return (
        <div className={styles.sideBar}>
            <div className={styles.logoContainer} onClick={()=>{handleSelectJob('')}}>
                <img className={styles.logo} src="logozocacapturapantalla.png"></img>
            </div>

            <ul className={styles.jobList}>
                <li className={`${styles.jobLink} ${selectedJob === 'users' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('users')}>
                    <AccessibilityOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Configurar novas usuarias
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'pedidos' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('pedidos')}>
                    <FormatListBulletedOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Gardar pedidos semanais
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'offers' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('offers')}>
                    <ReceiptLongOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Xestionar ofertas semanais
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'transfers' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('transfers')}>
                    <AddCardIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Cadrar extracto e tickets
                    </div>
                </li>
            </ul>

            <ul className={styles.jobList}>
                <li className={`${styles.jobLink} ${selectedJob === 'newDistributors' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('newDistributors')}>
                    <LocalShippingOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Engadir produtora
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'savedOrders' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('savedOrders')}>
                    <AssignmentTurnedInOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Ver pedidos gardados desta semana
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'unpaidReceipts' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('unpaidReceipts')}>
                    <PlaylistRemoveOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Ver recibos non pagos
                    </div>
                </li>
                <li className={`${styles.jobLink} ${selectedJob === 'savedOffers' ? styles.activeJob : ''}`} 
                    onClick={() => handleSelectJob('savedOffers')}>
                    <AssignmentTurnedInOutlinedIcon className={styles.icon}/>
                    <div className={styles.jobLinkText}>
                        Ver ofertas gardadas desta semana
                    </div>
                </li>
            </ul>
        </div>
    );
};

export default LeftSideBar;