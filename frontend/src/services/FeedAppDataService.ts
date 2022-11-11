import axios from 'axios';
import {AxiosError} from 'axios';
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
  }// Get specific users polls
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
  //Get all polls (For admins)
  async getAllPolls(username : string, password : string) {
    const response = await axios.get(BASE_URL+'polls/', {
      auth: {
        username: username,
        password: password
      }
    });
          return response.data;
  },
  async getAllUsers(username : string, password : string){
    const response = await axios.get(BASE_URL+'agents/', {
      auth: {
        username: username,
        password: password
      }
    });
          return response.data;
  },
  deletePoll(pollId : Number, username : string, password : string) {
    return;
  },
  deleteUser(username : string, adminUsername : string, password : string){
    return;
  },
  async postPoll(owner : string, question : string, startTime : string, endTime : string, isPrivate : Boolean, username : string, password : string) {
    try{
      const userIDResponse = await axios.get(BASE_URL+"agents/getID/"+owner);
      let userID = userIDResponse.data
      const response = await axios.post(BASE_URL+'polls', 
      {
        "yesCount":0,
        "noCount":0,
        "owner": {
          "agentID": userID
        },
        "question": question,
        "startTime":startTime,
        "endTime":endTime,
        "private":isPrivate,
        "pin":0
      }, {
        auth: {
          username: username,
          password: password
        }
      });
      return response.data;
    }catch(error){
      const err = error as AxiosError;
      this.handleError(err);
    }
  },
  postUser(username : string, email : string, password : string, ) {
    return;
  },
  makeAdmin(username : string, adminUsername : string, password : string) {
    return;
  },

  async postVote(bool : boolean, pollId : Number, username="", password="") {
    try{
      const response = await axios.post(BASE_URL+'polls/'+pollId+"/votes", 
      {
        "voter_username":username,
        "answer_yes":bool
      }, {
        auth: {
          username: username,
          password: password
        }
      });
      response.status
            return response.data;
    }catch(error){
      const err = error as AxiosError;
      this.handleError(err);
    }
  },
  handleError(error:Error) {
    if(axios.isAxiosError(error)) {
      if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.error("Request was made but server responded with status code: ",error.response.status,
        "Error data:",error.response.data,
        "Error headers:",error.response.headers);
      } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.error("Request made but no response:",error.request);
      } else {
        // Something happened in setting up the request that triggered an Error
        console.error('Request setup Error', error.message);
      }
      console.error("Error config:",error.config);
    }
  },
};