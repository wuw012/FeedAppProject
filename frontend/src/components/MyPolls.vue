<template>
  <div class="mypolls">
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
      <button class="btn btn-danger" @click="deleteThisPoll(poll.pollID)">Delete this poll</button>
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
      deletedPoll: false,
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
    },
    // DELETE POLLS LOGIC
    async deletePoll(pollID) {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.deletePoll(pollID, this.username, this.password)
      .then((status) => {
        if (status == 200) {
          this.deletedPoll = true;
        }
      }) 
    },
    deleteThisPoll(pollID) {
      this.deletePoll(pollID).then(()=> {
        if (this.deletedPoll) {
          alert("Poll deleted. Reloading page now")
          window.location.reload();
        }
      })
    }
  },
  mounted() {
    this.retrieveMyPolls()
  }
}
</script>

<style>
</style>