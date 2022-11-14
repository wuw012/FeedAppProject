import axios from 'axios';
import type {AxiosError} from 'axios';
import jsSHA from "jssha";

const BASE_URL = "http://localhost:8080/"

export default {
  // Simple hash function without Salt, only for demo purposes
  hashPwd(password : string) {
    let hashObj = new jsSHA("SHA-512", "TEXT", {numRounds: 1});
    hashObj.update(password);
    let hash = hashObj.getHash("HEX");
    return hash;
    },


  addAuthHeader(username : string, password : string){
    //const hashedPwd = this.hashPwd(password);
    return {auth: {
      "username":username,
      "password":password
    }}
  },


  async exists(username: string) {
    const response = await axios.get(BASE_URL+'agents/exists/' + username);
        return response.data;
  },


  async isAuthenticated(username : string, password : string) {
    const response = await axios.get(BASE_URL+'agents/isAuthenticated', this.addAuthHeader(username, password));
        return response.data;
  },
  
  async isAdmin(username : string) {
    const response = await axios.get(BASE_URL+'agents/isAdmin/'+username);
        return response.data;
  },
  
  // Get specific users polls
  async getPolls(username : string, password : string){
    const response = await axios.get(BASE_URL+'polls/'+username+'/userPolls', this.addAuthHeader(username, password));
        return response.data;
  },


  // Get poll with id, provice auth username and password if poll is private
  async getPoll(id : Number, username = "", password = "") {
    const response = await axios.get(BASE_URL+'polls/'+id, this.addAuthHeader(username, password));
          return response.data;
  },


  //Get all polls (For admins)
  async getAllPolls(username : string, password : string) {
    const response = await axios.get(BASE_URL+'polls/', this.addAuthHeader(username, password));
          return response.data;
  },


  async getAllUsers(username : string, password : string){
    const response = await axios.get(BASE_URL+'agents/', this.addAuthHeader(username, password));
          return response.data;
  },


  async deletePoll(pollId : Number, username : string, password : string) {
    try {

      const response = await axios.delete(BASE_URL+"polls/"+pollId, this.addAuthHeader(username, password))
      return response.status;
    }catch(error) {
      let err = error as AxiosError;
      this.handleError(err);
    }
  },
  async deleteUser(userId : Number, username : string, password : string) {
    try {
      const response = await axios.delete(BASE_URL+"agents/"+userId, this.addAuthHeader(username, password))
      return response.status;
    }catch(error) {
      let err = error as AxiosError;
      this.handleError(err);
    }
  },
  async getUserID(username : string) {
    try {
      const userIDResponse = await axios.get(BASE_URL+"agents/getID/"+username);
      return userIDResponse.data;
    }catch (error) {
      let err = error as AxiosError;
      this.handleError(err);
    }
  },


  async postPoll(question : string, startTime : string, endTime : string, isPrivate : Boolean, username : string, password : string) {
    try{
      const userID = await this.getUserID(username);
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
      }, this.addAuthHeader(username, password)
      );
      return response.status;
    }catch(error){
      const err = error as AxiosError;
      this.handleError(err);
    }
  },


  async postUser(username : string, email : string, password : string, ) {
    try {
      const response = await axios.post(BASE_URL+"agents/createUser", {
        "username":username,
        "email":email,
        "password":this.hashPwd(password),
        "role":"USER"
      })
      return response.status;
    }catch(error){
      const err = error as AxiosError;
      this.handleError(err);
    }
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
      }, this.addAuthHeader(username, password));
      response.status
            return response.status;
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