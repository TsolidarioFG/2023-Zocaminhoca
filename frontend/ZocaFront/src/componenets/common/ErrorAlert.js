import React from "react";
import styles from '../../styles/Alerts.module.css';
import CloseIcon from '@mui/icons-material/Close';

const ErrorAlert = ({message, onClose}) => {
    return (
        <div className={styles.errorAlert}>
            <div>
                {message}
            </div>
            <button className={styles.closeButton} onClick={onClose}>
                <CloseIcon/>
            </button>
        </div>
    )
}

export default ErrorAlert;