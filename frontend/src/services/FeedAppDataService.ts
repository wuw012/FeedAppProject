import axios from 'axios';

export default {
  async exists(username: string) {
    const response = await axios.get('http://localhost:8080/agents/exists/' + username);
        return response.data;
  }

  ,async isAuthenticated(username : string, password : string) {
    const response = await axios.get('http://localhost:8080/agents/isAuthenticated', {
        auth: {
            username: username,
            password: password
        }
    });
        return response.data;
  }
  ,async isAdmin(username : string) {
    const response = await axios.get('http://localhost:8080/agents/isAdmin/'+username);
        return response.data;
  }
  ,async getPoll(id:string){
    // Her kan du putte dummy data fram til vi får opp endpoint i API
    const dummyPoll = {Poll:id, question:"ThisIsADummyPoll"}
    let promise = new Promise(function(resolve, reject) {
        setTimeout(() => resolve(dummyPoll), 1000);
    });
    return promise;
  }
};