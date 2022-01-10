import config from 'config';
import {postRequest, postRequest_v2} from "../utils/ajax";
import {getBook} from "./bookService";
export function addOrder(userId, callback) {
    const url = `http://localhost:8080/addOrder?userId=${userId}`;
    postRequest(url, {}, callback);
}
export function placeOrder(postData, callback) {
    const url = `http://localhost:8080/placeOrder`;
    postRequest(url, postData, callback);
}

export function getAllOrders(callback) {
    const url = 'http://localhost:8080/getAllOrders';
    postRequest(url, {}, callback);
}
export function getAllOrdersForManager(callback) {
    const url = 'http://localhost:8080/admin/getAllOrders';
    postRequest(url, {}, callback);
}

export function searchOrders(time, callback) {
    const url = 'http://localhost:8080/searchOrders';
    postRequest(url, time, callback);
}
export function searchOrdersForManager(time, callback) {
    const url = 'http://localhost:8080/admin/searchOrders';
    postRequest(url, time, callback);
}
export function searchOrdersByKeyword(keyword, callback) {
    const url = `http://localhost:8080/searchOrdersByKeyword?keyword=${keyword}`;
    postRequest(url, {}, callback);
}
export function searchOrdersByKeywordForManager(keyword, callback) {
    const url = `http://localhost:8080/admin/searchOrdersByKeyword?keyword=${keyword}`;
    postRequest(url, {}, callback);
}



/*
export function addOrder(userId, callback) {
    const url = `https://localhost:8443/addOrder?userId=${userId}`;
    postRequest(url, {}, callback);
}
export function placeOrder(postData, callback) {
    const url = `https://localhost:8443/placeOrder`;
    postRequest(url, postData, callback);
}

export function getAllOrders(callback) {
    const url = 'https://localhost:8443/getAllOrders';
    postRequest(url, {}, callback);
}
export function getAllOrdersForManager(callback) {
    const url = 'https://localhost:8443/admin/getAllOrders';
    postRequest(url, {}, callback);
}

export function searchOrders(time, callback) {
    const url = 'https://localhost:8443/searchOrders';
    postRequest(url, time, callback);
}
export function searchOrdersForManager(time, callback) {
    const url = 'https://localhost:8443/admin/searchOrders';
    postRequest(url, time, callback);
}
export function searchOrdersByKeyword(keyword, callback) {
    const url = `https://localhost:8443/searchOrdersByKeyword?keyword=${keyword}`;
    postRequest(url, {}, callback);
}
export function searchOrdersByKeywordForManager(keyword, callback) {
    const url = `https://localhost:8443/admin/searchOrdersByKeyword?keyword=${keyword}`;
    postRequest(url, {}, callback);
}*/
