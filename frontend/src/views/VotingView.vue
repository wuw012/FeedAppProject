<template>
 
  <Poll :questionText="questionText" :pincode="pincode" :counter="counter"/>
</template>

<script setup lang="ts">
//:pincode="pincode" 
import Poll from "@/components/Poll.vue";

const pincode = 12345;
const questionText = "Do you like cats?";
const counter = 1;

</script>

<style scoped>
.h3 {
  display:block !important;
  align: right;
}
</style>
