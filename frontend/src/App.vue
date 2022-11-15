<template>
   
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">
        <img src="@/assets/voteballot.png" width="30" height="30" class="d-inline-block align-top" alt="">
        Feed App
      </a>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <RouterLink v-if="!isAdmin && userExists" class="nav-link" to="/mypolls">My Polls<span class="sr-only">(current)</span></RouterLink>
            <RouterLink v-if="isAdmin" class="nav-link" to="/admin">Admin dashboard<span class="sr-only">(current)</span></RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" to="/voting">Vote</RouterLink>
          </li>
        </ul>
        <div class="buttongroup">
          <button v-if="!loggedin()" class="btn btn-light btn-sm mr-2 float-right border border-primary" @click="redirectToLogin()"> Log in </button>
          <button v-if="loggedin()" class="btn btn-light btn-sm mr-2 float-right border border-primary" @click="logout()"> Log out </button>
        </div>
      </div>
    </nav>
  <RouterView />

  <br/>
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
  data() {
    return {
      username: "",
      isAdmin: false,
      userExists: false,
    }
  },
  methods: {
    redirectToLogin() {
      this.$router.push({path:"/login"})
    },
    logout() {
      localStorage.clear();
      //window.location.reload();
      this.$router.push({path:"/login"})
      document.location.href = "/login";
      
    },
    loggedin() {
      this.username = localStorage.getItem("username")
      if (this.username) {
        return true;
      } else {
        return false;
      }
    },
    async updateAuthStatus() {
            await FeedAppDataService.exists(this.username).then((userExists) => this.userExists = userExists);
            await FeedAppDataService.isAdmin(this.username).then((adminUser) => this.isAdmin = adminUser);
    },
    checkCredentials() {
            this.updateAuthStatus().then()
    }
  },
  mounted() {
      this.checkCredentials()
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
 

//FeedAppDataService.postUser("Donald_Rump", "dump@dahouse.com", "thesecurestofpasswordsinhistory").then((mrDump)=>console.log(mrDump));
//FeedAppDataService.postVote(true, 1, "Donald_Dump", "thesecurestofpasswordsinhistory").then((vote) => console.log("Bobs vote:", vote))


// DELETES
// Admin can delete anyones poll
//FeedAppDataService.deletePoll(2, "agent_007", "12345678").then((response) => console.log(response));
// Another user can't delete bobleifs poll
//FeedAppDataService.deletePoll(1, "69geir420", "totallyhashedandencryptedpassword").then((response) => console.log(response));
// Bobleif can delete his own poll
//FeedAppDataService.deletePoll(2, "bobleif", "totallyhashedandencryptedpassword")


// Attempt do delete user from user account
//FeedAppDataService.deleteUser(1, "bobleif", "totallyhashedandencryptedpassword").then((response) => console.log(response));
// Delete user from admin account
//FeedAppDataService.deleteUser(1, "agent_007", "12345678").then((response) => console.log(response));
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

a {
  margin-left: 10px;
}

#buttongroup {
  text-align: right;
}
</style>
