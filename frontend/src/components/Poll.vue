<template>
  <div class="poll" v-if="this.poll.status == 'ACTIVE'">
  <h3> ID: {{ $route.params.pollID }} </h3>
   
    <div class="question">
      <h1> Q: {{ this.poll.question }} </h1>
    </div>

    <div class="buttongroup">
      <button class="btn btn-primary btn-lg mr-2" type="submit" v-on:click="voteYes()"> YES </button>
      <button class="btn btn-primary btn-lg" type="submit" v-on:click="voteNo()">NO</button>
    </div>
   </div>

  <div class="poll" v-if="this.poll.status == 'FUTURE'">
    <h1>This poll hasn't been launched yet!! come back soon ;*</h1>
  </div>

  <div class="poll" v-if="this.poll.status == 'EXPIRED'">
    <h1>if youre reading this its too late :(</h1>
  </div>
</template>
  

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";
import FeedAppDataService from "@/services/FeedAppDataService";

export default {
  name: 'Poll',
  data() {
    return {
      poll: {},
      hasVoted: false,
    }
  },
  methods: {
    // HELPER METHODS
    redirectToThankyou() {
      this.$router.push({path: "/thankyou"})
    },
    retrieveUserCredentialsFromLocalStorage(){
        this.username = localStorage.getItem("username")
        this.password = localStorage.getItem("password")
    },
    // GET POLL LOGIC
    async getPoll() {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.getPoll(this.$route.params.pollID, this.username, this.password)
      .then((poll) => this.poll = poll);
    },
    retrievePoll() {
      this.getPoll().then()
    },
    // POST VOTE LOGIC
    async postVote( answer_yes : bool ) {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.postVote(answer_yes, this.$route.params.pollID, this.username, this.password)
      .then((status) => {
        if (status == 200) {
          this.hasVoted = true;
        }
      })
    },
    voteNo() {
      if (!this.hasVoted) {
        this.postVote(false).then(() => {
          if (this.hasVoted) {
            this.redirectToThankyou()
          }
        })
      }
    },
    voteYes() {
      this.postVote(true).then(() => {
        if (this.hasVoted) {
            this.redirectToThankyou()
          }
        })
    },
    
  },
  mounted() {
    this.retrievePoll()
  }
}

</script>

<style scoped>
h3{
  text-align:right;
}
</style>