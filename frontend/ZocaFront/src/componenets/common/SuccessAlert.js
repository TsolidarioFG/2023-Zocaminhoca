import React from "react";
import styles from '../../styles/Alerts.module.css';
import CloseIcon from '@mui/icons-material/Close';

const SuccessAlert = ({message, onClose}) => {
    return (
        <div className={styles.successAlert}>
            <div>
                {message}
            </div>
            <button className={styles.closeButton} onClick={onClose}>
                <CloseIcon/>
            </button>
        </div>
    )
}

export default SuccessAlert;