<template>
  <div class="mypolls">
    <button class="btn btn-primary" @click="retrieveMyPolls()"> Retrieve my polls </button>
    <br/>
    <br/>
    <ul v-for="poll in myPolls">
      <h5> Question: {{ poll.question }} </h5>
      <ul class="list-group">
        <li class="list-group-item"> Yes count: {{ poll.yesCount }}</li>
        <li class="list-group-item"> No count: {{ poll.noCount }}</li>
        <li class="list-group-item"> PollID: {{ poll.pollID }} </li>
        <li class="list-group-item"> Owner: {{ poll.owner.username }}</li>
        <li class="list-group-item"> Pin: {{ poll.pin }}</li>
        <li class="list-group-item"> Status: {{ poll.status }}</li>
        <li class="list-group-item"> Start time: {{ poll.startTime }}</li>
        <li class="list-group-item"> End time: {{ poll.endTime }}</li>
        <li class="list-group-item"> Private? {{ poll.private ? 'Yes' : 'No' }}</li>
      </ul>
      <br/>
    </ul>
  </div>
</template>
  

<script lang="ts">
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';

export default {
  name: 'MyPolls',
  data() {
    return {
      username: "",
      password: "",
      myPolls: [],
      error: "",
    }
  },
  methods: {
    retrieveUserCredentialsFromLocalStorage(){
            this.username = localStorage.getItem("username")
            this.password = localStorage.getItem("password")
    },
    async getMyPolls() {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.getPolls(this.username, this.password)
      .then((myPolls) => this.myPolls = myPolls);
    },
    retrieveMyPolls() {
      this.getMyPolls().then()
    }
  }
}
</script>

<style>
</style>