import React, {useState, useEffect} from 'react';
import FileSelectorInput from './FileSelector';
//import { readPedidos } from '../../backend/PedidoService';
import styles from '../../styles/SelectPedidosFile.module.css';

const SelectFile = ({setFilename, selectorText}) => {

    const [selectedFilename, setSelectedFilename] = useState('');

    const handleFileSelect = (filename) => {
        setSelectedFilename(filename);
    }

    const confirmFilename = () => {
        setFilename(selectedFilename);
    }

    return (
        <div className={styles.fileSelectorContainer}>
            <div>{selectorText}</div>
            {selectedFilename !== "" && <span className={styles.fileSelected}>{selectedFilename}</span>}
            <div className={styles.selectFileButtons}>
                <FileSelectorInput setFilename={handleFileSelect}/>
                {selectedFilename !== "" && <button className={styles.confirmFileButton} onClick={confirmFilename}>Confirmar</button>}
            </div>
        </div>
    )

}

export default SelectFile;