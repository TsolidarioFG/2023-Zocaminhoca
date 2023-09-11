import { apiGet, apiPost } from "../util/apiFetch";

export const getAllJobs = (onSuccess, onError) => {
    apiGet('/jobs', onSuccess, onError);
}

export const markJobAsDone = (jobId, onSuccess, onError) => {
    apiPost(`/jobs/${jobId}`,null, onSuccess, onError);
}