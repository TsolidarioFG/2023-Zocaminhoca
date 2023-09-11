import React, {useState} from 'react';
import SelectFile from '../common/SelectFile';
import OrdersList from './OrdersList';

const PedidosJob = ({setSuccessAlert, setErrorAlert, jobId}) => {

    const [pedidosFilename, setPedidosFilename] = useState('');

    const handleSetFilename = (filename) => {
        setPedidosFilename(filename);
    }

    return(
        <>
            {pedidosFilename === '' ?  <SelectFile setFilename={handleSetFilename} selectorText={"Selecciona arquivo de pedidos"} />
            : <OrdersList pedidosFilename={pedidosFilename} setSuccessAlert={setSuccessAlert} setErrorAlert={setErrorAlert} jobId={jobId}/> }
        </>
        
    )
}

export default PedidosJob;