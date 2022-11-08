<template>
  <div class="vote">
    <div class="yesCounter">
      <button @click="$emit('increaseYes')"> YES </button>
      <h3> {{ yesCount }} </h3>
    </div>
    <div class="noCounter">
      <button @click="$emit('increaseNo', 1)"> NO </button>
      <h3>{{ noCount }}</h3>
    </div>
  </div>
</template>


<script setup lang="ts">

let yesCount = 0;
function increaseYes() {
  yesCount +=  1
};

let noCount = 0;
function increaseNo() {
  noCount += 1
}
</script>

<style>
.vote{}
</style>
