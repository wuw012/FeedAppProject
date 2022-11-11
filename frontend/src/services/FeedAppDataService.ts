import axios from 'axios';
const BASE_URL = "http://localhost:8080/"
export default {
  async exists(username: string) {
    const response = await axios.get(BASE_URL+'agents/exists/' + username);
        return response.data;
  }

  ,async isAuthenticated(username : string, password : string) {
    const response = await axios.get(BASE_URL+'agents/isAuthenticated', {
        auth: {
            username: username,
            password: password
        }
    });
        return response.data;
  }
  ,async isAdmin(username : string) {
    const response = await axios.get(BASE_URL+'agents/isAdmin/'+username);
        return response.data;
  }
  ,async getPolls(username : string, password : string){
    const response = await axios.get(BASE_URL+'polls/'+username+'/userPolls', {
    auth: {
      username: username,
      password: password
    }
  });
        return response.data;
  },
  async getPublicPoll(id : string, username = "", password = "") {
    const response = await axios.get(BASE_URL+'polls/'+id, {
      auth: {
        username: username,
        password: password
      }
    });
          return response.data;
  }
};