<template>
  <div class="allusers">
    <br/>
    <ul v-for="user in allUsers">
      <h5> {{ user.username }} </h5>
      <ul class="list-group">
        <li class="list-group-item"> AgentID: {{ user.agentID }}</li>
        <li class="list-group-item"> Email: {{ user.email }}</li>
        <li class="list-group-item"> Role: {{ user.role }}</li>
      </ul>
      <br/>
      <button class="btn btn-danger" @click="deleteThisUser(user.agentID)">Delete this user</button>
    </ul>
  </div>
  <br/>
  <br/>

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
      deletedUser: false,
    }
  },
  methods: {
    retrieveUserCredentialsFromLocalStorage(){
        this.username = localStorage.getItem("username")
        this.password = localStorage.getItem("password")
    },

    // GET ALL USERS LOGIC
    async getAllUsers() {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.getAllUsers(this.username, this.password)
      .then((allUsers) => this.allUsers = allUsers);
    },
    retrieveAllUsers() {
      this.getAllUsers().then()
    },
    // DELETE USER LOGIC
    async deleteUser(agentID) {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.deleteUser(agentID, this.username, this.password)
      .then((status) => {
        if (status == 200) {
          this.deletedUser = true;
        }
      }) 
    },
    deleteThisUser(agentID) {
      console.log(agentID)
      this.deleteUser(agentID).then(()=> {
        if (this.deletedUser) {
          alert("User deleted. Reloading page now")
          window.location.reload();
        }
      })
    }
  },
  mounted() {
    this.retrieveAllUsers()
  }
}
</script>

<style>
</style>