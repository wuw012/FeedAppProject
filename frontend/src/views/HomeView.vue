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

</script>