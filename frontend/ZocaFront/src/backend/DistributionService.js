import {apiGet, apiPost} from "../util/apiFetch"

export const findDistributorByName = (queryName, onSuccess, onError) => {
    apiGet(`/distribution/distributors?queryName=${queryName}`, onSuccess, onError);
}

export const addNewDistributor = (distributor, onSuccess, onError) => {
    apiPost('/distribution/distributors', distributor, onSuccess, onError);
}

export const addDistributorOffers = (distributors, onSuccess, onError) => {
    apiPost('/distribution/offers', distributors, onSuccess, onError);
}

export const getWeeklyOffers = (onSuccess, onError) => {
    apiGet('/distribution/offers', onSuccess, onError);
}

export const getSavedOffers = (onSuccess, onError) => {
    apiGet('/distribution/offers/saved', onSuccess, onError)
}