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
  // Get poll with id, provice auth username and password if poll is private
  async getPoll(id : Number, username = "", password = "") {
    const response = await axios.get(BASE_URL+'polls/'+id, {
      auth: {
        username: username,
        password: password
      }
    });
          return response.data;
  },
  async postVote(bool : boolean, pollId : Number, username="", password="") {
    const response = await axios.post(BASE_URL+'polls/'+pollId, 
    {
      "voter_username":username,
      "answer_yes":bool
    }, {
      auth: {
        username: username,
        password: password
      }
    });
          return response.data;
  },
  // INGVILD SOM TULLER :) setter ikke role i signupform
  // fikk statuskode 400 så denne funker ikke hehehehe lykke til
  async postUser(username="", email="", password="", role="USER") {
    const response = await axios.post(BASE_URL+'agents/', 
    {
      "username": username,
      "email": email,
      "password": password,
      "role": role
    });
          return response.status; //returner 200 hvis ikke endre i components/signupform.vue
  },
  async postPoll(question="", startTime="", endTime="", isPrivate : boolean,  username="", password="") {
    // const owner = getIDfromUsername(username): return { "agentID": agentID } 
    const agentID = 10;
    const owner = { "agentID": agentID}
    //generate new ID? get last poll -> pollID + 1
    const pollId = 10;

    const response = await axios.post(BASE_URL+'polls/'+pollId, 
    {
      "yesCount":0,
      "noCount":0,
      "owner": owner,
      "question":question,
      "startTime":startTime,
      "endTime":endTime,
      "private":isPrivate,
    }, {
      auth: {
        username: username,
        password: password
      }
    });
        return response.status; //returner 200 hvis ikke endre i components/createpollform.vue
  }
};