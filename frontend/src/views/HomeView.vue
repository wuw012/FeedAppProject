<template>
  <h1>HOME VIEW</h1>
  <div v-if="isLoading">Loading ...</div>
  <div v-else>
    <h2>User Profile</h2>
    <button @click="login">Log in</button>
    <pre v-if="isAuthenticated">
        <code>{{ user }}</code>
      </pre>
  </div>
  <div>
    <button @click="logout">Log out</button>
  </div>
</template>
<script lang="ts">
// Composition API
import { useAuth0 } from '@auth0/auth0-vue';


export default {
  setup() {
    const auth0 = useAuth0();
    return {
      login() {
        auth0.loginWithRedirect();
        
      },
      logout() {
          auth0.logout({ returnTo: window.location.origin });
        },
      user: auth0.user,
      isAuthenticated: auth0.isAuthenticated, // Always check before consuming user
      isLoading: auth0.isLoading, // Always check before accessing isAuthenticated
    };

  }
/* 
  created() {
    let loginStatus = this.$cookies.get();
  } */
};

</script>