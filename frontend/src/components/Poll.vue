<template>
   <div class="poll">
   <h3> ID: {{ pincode }} </h3>
    <div class="question">
      <h1> Q: {{ questionText }} </h1>
    </div>

    <div class="buttongroup">
      <button class="btn btn-primary btn-lg mr-2" type="submit" v-on:click="yesCounter += 1"> <RouterLink to="/thankyou">YES</RouterLink> </button>
      
      <button class="btn btn-primary btn-lg" @click="incrementCounter">NO</button>
    </div>
    <p> no count:{{ counter }} </p>
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
  data() {
    return {
      title: "Counter",
      counter: 0
    };
  },
  methods: {
    incrementCounter() {
      this.counter++;
      },
    decrementCounter() {
      this.counter--;
      }
    }
  }
}
//<button @click="count++">You clicked me --  {{ count }} -- times.</button>
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