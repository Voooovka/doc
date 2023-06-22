import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/app/user/';

class UserService {


    getUser(id){
        return axios.get(`${API_URL}${id}`, { headers: authHeader() })
    }

    earnMoney(email){
        return axios.put(`${API_URL}earn-money`,{email}, { headers: authHeader() })
    }

    sendMoney(emailFrom, emailTo, amount){
        return axios.put(`${API_URL}send-money`,{emailFrom, emailTo, amount}, { headers: authHeader() });
    }

}

export default new UserService();
