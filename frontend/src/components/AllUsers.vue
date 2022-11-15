<template>
<div class="allUsers">
    <table id="allUsers" class="table table-striped" style="width:100%">
          <thead>
              <tr>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th></th>
              </tr>
          </thead>
          <tbody v-for="user in allUsers">
              <tr>
                  <td>{{ user.username }}</td>
                  <td>{{ user.email }}</td>
                  <td>{{ user.role }}</td>
                  <td><button class="btn btn-danger btn-sm" @click="deleteThisUser(user.agentID)">Delete</button></td>
              </tr>
          </tbody>
    </table>
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