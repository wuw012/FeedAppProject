<template>
    <div class="wrapper">
      <nav>
        <RouterLink to="/mypolls">My Polls</RouterLink>
        <RouterLink to="/createpolls">Create Poll</RouterLink>
        <RouterLink to="/voting">Voting</RouterLink>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/signup">Signup</RouterLink>
      </nav>
    </div>
  <RouterView />
</template>

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";
import FeedAppDataService from "./services/FeedAppDataService";
import VotingView from "@/views/VotingView.vue";
import AboutView from  "@/views/AboutView.vue";
import ThankyouView from "@/views/ThankyouView.vue";
import MypollsView from "@/views/MypollsView.vue";
import AltLoginView from "@/views/AltLoginView.vue";
import CreatepollsView from "@/views/CreatepollsView.vue";
import SignupView from "@/views/SignupView.vue";

export default {
  name: "App",
  components:
    {
      VotingView,
      AboutView,
      ThankyouView,
      MypollsView,
      AltLoginView,
      CreatepollsView,
      SignupView
    }
}

// Sånn her løser man "pending" i vanlig js-stil:         
//                                  Resultat fra "exists" lagret i variabel
// |-------async funksjon-----------|----------v       
FeedAppDataService.exists("bobleif").then((bobExists) => console.log("Bob exists:",bobExists));
//                                    |----| 
//                    "Wait until Promise is done Pending 'then' do something"

// Auth test (sjekk browser log)
FeedAppDataService.isAuthenticated("bobleif", "totallyhashedandencryptedpassword").then((bobAuthenticated) => console.log("Bob is strapped in and ready to go",bobAuthenticated));

// isAdmin test (Should be false)
FeedAppDataService.isAdmin("bobleif").then((cookiesTantaDi) => console.log("Sa du?",cookiesTantaDi));

// isAdmin test (Should be true)
FeedAppDataService.isAdmin("agent_007").then((NOTstirredFFS) => console.log("agent_007 is admin",NOTstirredFFS));

FeedAppDataService.getPolls("bobleif", "totallyhashedandencryptedpassword").then((dummyPoll) => console.log(dummyPoll));

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
</style>
