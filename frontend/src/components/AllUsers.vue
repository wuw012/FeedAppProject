<template>
  <div class="allusers">
    <button class="btn btn-primary" @click="retrieveAllUsers()"> Retrieve all users </button>
    <br/>
    <br/>
    <ul v-for="user in allUsers">
      <h5> {{ user.username }} </h5>
      <ul class="list-group">
        <li class="list-group-item"> AgentID: {{ user.agentID }}</li>
        <li class="list-group-item"> Email: {{ user.email }}</li>
        <li class="list-group-item"> Role: {{ user.role }}</li>
      </ul>
      <br/>
    </ul>
  </div>
</template>
  

<script lang="ts">
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';

export default {
  name: 'AllUsers',
  data() {
    return {
      username: "",
      password: "",
      allUsers: [],
      error: "",
    }
  },
  methods: {
    retrieveUserCredentialsFromLocalStorage(){
        this.username = localStorage.getItem("username")
        this.password = localStorage.getItem("password")
    },
    async getAllUsers() {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.getAllUsers(this.username, this.password)
      .then((allUsers) => this.allUsers = allUsers);
    },
    retrieveAllUsers() {
      this.getAllUsers().then()
    }
  }
}
</script>

<style>
</style>