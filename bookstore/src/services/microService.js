import { postRequest, postRequest_v3} from "../utils/ajax";

export function getAuthorByTitle(keyword, callback) {
    const url = `http://localhost:8081/books/author/${keyword}`;
    postRequest(url, {}, callback);
}