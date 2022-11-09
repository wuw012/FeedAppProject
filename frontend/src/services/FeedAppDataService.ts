import axios from 'axios';


export default {
  exists(username: string) {
    return axios.get('http://localhost:8080/agents/exists/'+username).then((response) => {
        return response.data;
    });
  }
};