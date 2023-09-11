import React, { useState } from 'react';
import styles from '../../styles/FileSelector.module.css'

const FileSelectorInput = ({setFilename}) => {

    const handleFileSelect = (e) => {
        const file = e.target.files[0];
        if (file) {
            setFilename(file.name);
        }
    }

    return(
        <div>
            <label htmlFor="file-selector" className={styles.fileSelector}>
                Selecciona un Arquivo
            </label>
            <input id="file-selector" className={styles.inputFile} type="file" onChange={handleFileSelect}/>
        </div>
    )

}

export default FileSelectorInput;