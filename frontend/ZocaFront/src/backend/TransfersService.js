import { apiGet, apiPost} from "../util/apiFetch";

export const getCheckedTransfers = (filename, onSuccess, onError) => {
    apiGet(`/bills/transfers/${filename}`, onSuccess, onError)
}

export const writeCheckedTransfers = (filename, transfers, onSuccess, onError) => {
    apiPost(`/bills/transfers/${filename}`, transfers, onSuccess, onError)
}

export const getUnpaidReceipts = (onSuccess, onError) => {
    apiGet('/bills/unpaid', onSuccess, onError);
}