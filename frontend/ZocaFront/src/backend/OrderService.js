import { apiGet, apiPost } from "../util/apiFetch";

export const readPedidos = (filename, onSuccess, onError) => {
    apiGet(`/pedidos/${filename}`, onSuccess, onError)
}

export const savePedidos = (pedidos, onSuccess, onError) => {
    apiPost(`/pedidos/save`, pedidos, onSuccess, onError)
}

export const getWeekOrders = (onSuccess, onError) => {
    apiGet('/pedidos/week',onSuccess,onError);
}