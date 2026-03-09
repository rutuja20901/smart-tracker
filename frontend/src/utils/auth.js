//Auth helper

import { getToken } from "./jwt";

export const isAuthenticated = () =>{
    return !!getToken();
};