import { apiGet,apiPost } from "../util/apiFetch";

export const getNewUsers = (onSuccess, onError) => 
    apiGet("/user/new", onSuccess, onError);

export const updateUser = (user, onSuccess, onError) => {
    apiPost("/user", user, onSuccess, onError);
}

export const searchClientByName = (clientName, onSuccess, onError) => {
    apiGet(`/user/client?queryName=${clientName}`, onSuccess, onError);
}
