<template>
    <div class="wrapper">
      <nav>
        <RouterLink to="/mypolls">My Polls</RouterLink>
        <RouterLink to="/createpolls">Create Poll</RouterLink>
        <RouterLink to="/voting/1">Voting on poll 1</RouterLink>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/signup">Signup</RouterLink>
        <RouterLink to="/admin">Admin</RouterLink>
      </nav>
      <div class="buttongroup">
        <button class="btn btn-light btn-sm mr-2" @click="redirectToLogin()"> Log in </button>
        <button class="btn btn-light btn-sm" @click="logout()"> Log out </button>
      </div>
    </div>
  <RouterView />
</template>

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";
import FeedAppDataService from "./services/FeedAppDataService";
import VotingView from "@/views/VotingView.vue";
import ThankyouView from "@/views/ThankyouView.vue";
import MypollsView from "@/views/MypollsView.vue";
import AltLoginView from "@/views/AltLoginView.vue";
import CreatepollsView from "@/views/CreatepollsView.vue";
import SignupView from "@/views/SignupView.vue";
import { conditionalExpression } from "@babel/types";

export default {
  name: "App",
  components: {
    VotingView,
    ThankyouView,
    MypollsView,
    AltLoginView,
    CreatepollsView,
    SignupView
  },
  methods: {
    redirectToLogin() {
      this.$router.push({path:"/login"})
    },
    logout() {
      localStorage.clear();
      alert("Sucessfully logged you out! Welcome back :)")
      this.$router.push({path:"/login"})
    }
  }
}


// Testing
// Sånn her løser man "pending" i vanlig js-stil:         
//                                  Resultat fra "exists" lagret i variabel
// |-------async funksjon-----------|----------v       
//FeedAppDataService.exists("bobleif").then((bobExists) => console.log("Bob exists:",bobExists));
//                                    |----| 
//                    "Wait until Promise is done Pending 'then' do something"

// Auth test (sjekk browser log)
//FeedAppDataService.isAuthenticated("bobleif", "totallyhashedandencryptedpassword").then((bobAuthenticated) => console.log("Bob is strapped in and ready to go",bobAuthenticated));

// isAdmin test (Should be false)
//FeedAppDataService.isAdmin("bobleif").then((cookiesTantaDi) => console.log("Sa du?",cookiesTantaDi));

// isAdmin test (Should be true)
//FeedAppDataService.isAdmin("agent_007").then((NOTstirredFFS) => console.log("agent_007 is admin",NOTstirredFFS));

//FeedAppDataService.getPolls("bobleif", "totallyhashedandencryptedpassword").then((dummyPoll) => console.log(dummyPoll));


//FeedAppDataService.getPoll(4,"agent_007", "12345678").then((poll) => console.log(poll))
//FeedAppDataService.getPoll(4).then((poll) => console.log(poll)) 

//FeedAppDataService.postVote(true, 2).then((vote) => console.log("Anonymous vote:", vote))
//FeedAppDataService.postVote(true, 1, "bobleif", "totallyhashedandencryptedpassword").then((vote) => console.log("Bobs vote:", vote))
//FeedAppDataService.postVote(true, 1).then((vote) => console.log("Rejected anonymous vote:", vote))
//FeedAppDataService.getUserID("bobleif").then((id) => console.log(id));
 
//FeedAppDataService.postPoll("Kan Vue poste polls?", 
//"2022-10-10 10:00:00", "2023-10-12 10:00:00", 
//false, "bobleif", "totallyhashedandencryptedpassword").then((poll) => console.log("Bobleifs new poll:", poll))
 

//FeedAppDataService.postUser("Donald_Dump", "dump@dahouse.com", "thesecurestofpasswordsinhistory").then((mrDump)=>console.log(mrDump));
//FeedAppDataService.postVote(true, 1, "Donald_Dump", "thesecurestofpasswordsinhistory").then((vote) => console.log("Bobs vote:", vote))
//FeedAppDataService.deletePoll(1, "bobleif", "totallyhashedandencryptedpassword")
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

a {
  margin-left: 10px;
}
</style>
