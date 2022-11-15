<template>
   <div class="mypolls">
    <br/>
    <h1>My Polls</h1>
    <MyPolls />
    <button class="btn btn-success btn-lg" @click="redirectToCreatePolls()"> Create new poll</button>
   </div>
</template>
  

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";
import MyPolls from "@/components/MyPolls.vue"

export default {
  name: 'MyPollsView',
  components: {
    MyPolls
  },
  methods: {
    redirectToCreatePolls() {
      this.$router.push({path: "/createpolls"})
    }
  }
}
</script>
