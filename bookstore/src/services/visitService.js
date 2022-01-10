import {postRequest} from "../utils/ajax";

export const visitHomePage = (callback) => {
    const url = `http://localhost:8080/visitHomePage`;
    postRequest(url, {}, callback);
};

/*
export const visitHomePage = (callback) => {
    const url = `https://localhost:8443/visitHomePage`;
    postRequest(url, {}, callback);
};*/
