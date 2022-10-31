<template>
   <div class = "poll">
    <Question />
    <div class="mt-3">
    <b-button-group size="sm">
      <b-button>Left</b-button>
      <b-button>Middle</b-button>
      <b-button>Right</b-button>
    </b-button-group>
  </div>
   </div>
</template>
  

<script setup lang="ts">
import Question from "@/components/subcomponents/Question.vue";
import Vote from "@/components/subcomponents/Vote.vue";
</script>

<style scoped>
.poll{
      text-align: center;
    }

@media (min-width: 1024px) {
    .greetings h1,
    .greetings h3 {
      text-align: left;
    }
  }
  </style>