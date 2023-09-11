import React from "react";
import AccessibilityOutlinedIcon from '@mui/icons-material/AccessibilityOutlined';
import FormatListBulletedOutlinedIcon from '@mui/icons-material/FormatListBulletedOutlined';
import ReceiptLongOutlinedIcon from '@mui/icons-material/ReceiptLongOutlined';
import AddCardIcon from '@mui/icons-material/AddCard';
import styles from '../../styles/JobCard.module.css'

const JobCard = ({job, setJob}) => {

    const jobIcon = () => {
        switch (job.name) {
            case 'users':
                return <AccessibilityOutlinedIcon className={styles.jobIcon}/>
            case 'pedidos':
                return <FormatListBulletedOutlinedIcon className={styles.jobIcon}/>
            case 'offers':
                return <ReceiptLongOutlinedIcon className={styles.jobIcon}/>
            case 'transfers':
                return <AddCardIcon className={styles.jobIcon}/>
        }
    }

    const jobLabel = () => {
        switch (job.name) {
            case 'users':
                return 'Configurar novas usuarias'
            case 'pedidos':
                return 'Gardar pedidos semanais'
            case 'offers':
                return 'Xestionar ofertas semanais'
            case 'transfers':
                return 'Cadrar extracto e tickets'
        }
    }

    const formatDate = (doneDate) => {
        if (!doneDate) {
            return '';
        }
        const date = new Date(doneDate);
        const day = String(date.getDate()).padStart(2,'0');
        const month = String(date.getMonth()+1).padStart(2,'0');
        const year = date.getFullYear();

        const formattedDate= `${day}/${month}/${year}`;
        return formattedDate;
    }
    
    return (
        <div className={styles.jobCard} onClick={() => {setJob(job.name)}}>
            <div className={styles.jobLabel}>
                {jobLabel()}
            </div>
            {jobIcon()}
            <div>
                {job.name==='pedidos' || job.name==='offers' ? `Estado: ${job.done ? 'feita' : 'pendente'}` : ''}
            </div>
            <div>
                {`Ultima vez realizada: ${formatDate(job.doneDate)}`}
            </div>
        </div>
    )

}

export default JobCard;