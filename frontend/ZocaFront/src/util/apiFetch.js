export const apiGet = (path, onSuccess, onError) => {
    const config = {}
    config.method = 'GET';
    config.headers = {
        'Accept': 'application/json, */*',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    };
    fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}${path}`,config)
        .then(response => handleResponse(response,onSuccess,onError))
}

export const apiPost = (path, body, onSuccess, onError) => {
    const config = {}
    config.method = 'POST';
    config.headers = {
        'Accept': 'application/json, */*',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    };
    config.body = JSON.stringify(body);

    fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}${path}`, config)
        .then(response => handleResponse(response,onSuccess,onError))
}

const handleOkResponse = (response, onSuccess) => {
    if(!response.ok) {
        return false;
    }

    if (!onSuccess) {
        return true;
    }

    if(response.status === 204) {
        onSuccess();
        return true;
    }

    response.json().then(payload => onSuccess(payload));

    return true;
}

const handleErrorResponse = (response, onErrors) =>{
    if (response.status > 300) {
        response.json().then(payload => {
            onErrors(payload);
        })
        return true;
    }
    return false;
}

const handleResponse = (response, onSuccess, onError) => {
    if(handleOkResponse(response, onSuccess)) {
        return;
    }

    if(handleErrorResponse(response, onError)) {
        return;
    }

    throw new NetworkError();
}