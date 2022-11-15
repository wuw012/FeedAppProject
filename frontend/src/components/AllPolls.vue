<template>
  <div class="allPolls">
    <table id="allPolls" class="table table-striped" style="width:100%">
          <thead>
              <tr>
                  <th>Question</th>
                  <th>Owner</th>
                  <th>Yes count</th>
                  <th>No count</th>
                  <th>Private</th>
                  <th>Status</th>
                  <th>Start date</th>
                  <th>End date</th>
                  <th>Link</th>
                  <th></th>
              </tr>
          </thead>
          <tbody v-for="poll in allPolls">
              <tr>
                  <td>{{ poll.question }}</td>
                  <td>{{ poll.owner.username }}</td>
                  <td>{{ poll.yesCount }}</td>
                  <td>{{ poll.noCount }}</td>
                  <td>{{ poll.private ? 'Yes' : 'No' }}</td>
                  <td>{{ poll.status }}</td>
                  <td>{{ poll.startTime }}</td>
                  <td>{{ poll.endTime }}</td>
                  <td> <button class="btn btn-link" @click=redirectToPoll(poll.pollID)> http://feedapp.no/voting/{{ poll.pollID }}</button></td>
                  <td><button class="btn btn-danger btn-sm" @click="deleteThisPoll(poll.pollID)">Delete</button></td>
              </tr>
          </tbody>
    </table>
  </div>
</template>
  

<script lang="ts">
import FeedAppDataService from "@/services/FeedAppDataService";

export default {
  name: 'AllPolls',
  data() {
    return {
      username: "",
      password: "",
      allPolls: [],
      error: "",
      deletedPoll: false,
    }
  },
  methods: {
    retrieveUserCredentialsFromLocalStorage(){
        this.username = localStorage.getItem("username")
        this.password = localStorage.getItem("password")
    },

    // GET ALL POLLS LOGIC
    async getAllPolls() {
      this.retrieveUserCredentialsFromLocalStorage();
      await FeedAppDataService.getAllPolls(this.username, this.password)
      .then((allPolls) => this.allPolls = allPolls);
    },
    retrieveAllPolls() {
      this.getAllPolls().then()
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
    },
    redirectToPoll(pollID){
      this.$router.push({path: "/voting/"+pollID})
    }
  },
  mounted() {
    this.retrieveAllPolls()
  }
}
</script>

<style>
</style>