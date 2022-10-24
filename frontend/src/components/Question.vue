<template>
  <div class="question">
    <h1>"Do you like cats?"</h1>
    <h3>answer below</h3>
  </div>
</template>

<script setup lang="ts">

//export defineProps(msg) 

</script>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>