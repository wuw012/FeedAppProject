<template>
   <div class="mypolls">

    <div class="header">
      <h1>My polls </h1>
    </div>

    

    <div class="buttongroup">
      <button class="btn btn-secondary btn-lg">  <RouterLink to="/createpolls">CREATE NEW POLL</RouterLink> </button>
    </div>

    <div class="posts">
      <Posts/>
    </div>

   </div>
</template>
  

<script lang="ts">
import Posts from '@/components/Posts.vue';
import { RouterLink, RouterView } from "vue-router";

export default {
  name: 'MyPollsView',
  components: {
    Posts
  }
}

</script>

<style scoped>
#posts {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.question{
  display: block;
}
.poll{
      text-align: center;
    }
.button{
      text-align: center;
      margin: 2%;
    }
</style>