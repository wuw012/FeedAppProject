<template>
   <div class="mypolls">
      <h1>My polls </h1>
      <AllPolls />
      <button class="btn btn-secondary" @click="redirectToCreatePolls()"> Create new poll</button>
   </div>
</template>
  

<script lang="ts">
import Posts from '@/components/Posts.vue';
import { RouterLink, RouterView } from "vue-router";

export default {
  name: 'MyPollsView',
  components: {
    Posts
  },
  methods: {
    redirectToCreatePolls() {
      this.$router.push({path: "/createpolls"})
    }
  }
}
</script>

<style>
</style>