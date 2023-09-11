import React, {useState} from 'react';
import { addNewDistributor } from '../../backend/DistributionService';
import styles from '../../styles/AddDistributor.module.css'

const AddDistributor = ({setSuccessAlert, setErrorAlert}) => {

    const emptyDistributor = {
        id: null,
        name: '',
        email: '',
        phone: '',
        address: ''
    }

    const [distributor, setDistributor] = useState({...emptyDistributor});



    const handleChange = (e) => {
        const { name, value} = e.target;
        setDistributor((oldDistributor) => ({
            ...oldDistributor,
            [name]: value
        }));
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        addNewDistributor(distributor, ()=>{    
            setDistributor(emptyDistributor);
            setSuccessAlert("Produtora gardada con éxito!")
        },(error)=>{setErrorAlert(error.message)})
    }

    return (
        <form className={styles.form} onSubmit={handleSubmit}>
            <div className={styles.inputContainer}>
                <label className={styles.inputLabel} htmlFor="name">Nombre</label>
                <input className={styles.input} type="text" id="name" name="name" value={distributor.name} onChange={handleChange} placeholder='Nome'/>
            </div>
            <div className={styles.inputContainer}>
                <label className={styles.inputLabel} htmlFor="email">Email</label>
                <input className={styles.input} type="text" id="email" name="email" value={distributor.email} onChange={handleChange} placeholder='Email'/>
            </div>
            <div className={styles.inputContainer}>
                <label className={styles.inputLabel} htmlFor="phone">Teléfono</label>
                <input className={styles.input} type="text" id="phone" name="phone" value={distributor.phone} onChange={handleChange} placeholder='Telefono'/>
            </div>
            <div className={styles.inputContainer}>
                <label className={styles.inputLabel} htmlFor="address">Direccion</label>
                <input className={styles.input} type="text" id="address" name="address" value={distributor.address} onChange={handleChange} placeholder='Dirección'/>
            </div>
            <button type='submit' className={styles.confirmButton}>Añadir</button>
        </form>
    )

}

export default AddDistributor;