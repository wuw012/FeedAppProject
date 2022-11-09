<template>
    <div class="wrapper">

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/voting">Voting</RouterLink>
      </nav>
    </div>
  <RouterView />
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import VotingView from "./views/VotingView.vue";
import FeedAppDataService from "./services/FeedAppDataService";



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

FeedAppDataService.getPoll("1234").then((dummyPoll) => console.log("Result of dummy promise: ",dummyPoll));
</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}


@media (min-width: 1024px) {
  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
