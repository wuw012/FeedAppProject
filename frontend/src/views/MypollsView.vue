<template>
   <div class="mypolls">
    <h1>My Polls</h1>
    <button class="btn btn-secondary btn-sm" @click="redirectToCreatePolls()"> Create new poll</button>
    <br/><br/>
    <MyPolls />
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
