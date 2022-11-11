<template>
   <div class="poll">
   <h3> ID: {{ pincode }} </h3>
    <div class="question">
      <h1> Q: {{ questionText }} </h1>
    </div>

    <div class="buttongroup">
      <button class="btn btn-primary btn-lg mr-2" type="submit" v-on:click="redirectToThankyou()"> YES </button>
      
      <button class="btn btn-primary btn-lg" type="submit" v-on:click="redirectToThankyou()">NO</button>
    </div>
   </div>
</template>
  

<script lang="ts">
import { RouterLink, RouterView } from "vue-router";

export default {
  name: 'Poll',
  props: {
    questionText: {
      type: String,
    },
    pincode: {
      type: Number,
    },
    counter: {
      type: Number,
    },
  },
  methods: {
    redirectToThankyou() {
      this.$router.push({path: "/thankyou"})
      }
  }
}

</script>

<style scoped>
a{
  color: #fff;
}
a:hover{
  background-color: #007bff;
}

h3{
  text-align:right;
}

.question{
  display: block;
}
.poll{
      text-align: center;
    }
.button{
      text-align: center;
      margin: 200em;
    }
</style>