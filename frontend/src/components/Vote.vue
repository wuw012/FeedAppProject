<template>
  <div>
    <div class="yesCounter">
      <button @click="$emit('increaseYes', 1)">yes i do :))</button>
      <h3> {{ yesCount }} </h3>
    </div>
    <div class="noCounter">
      <button @click="$emit('increaseNo', 1)">absolutely not</button>
      <h3>{{ noCount }}</h3>
    </div>
  </div>
</template>


<script setup lang="ts">

let yesCount = 0;
function increaseYes(n) {
  yesCount.valueOf += n
}

let noCount = 0;
function increaseNo(n) {
  noCount.valueOf += n
}



</script>

<style>
.yesCounter {
  
}

.noCounter {

}
</style>
