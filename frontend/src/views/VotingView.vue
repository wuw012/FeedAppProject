<template>
 
  <Poll :questionText="questionText"/>
</template>

<script setup lang="ts">
//:pincode="pincode" 
import Poll from "@/components/Poll.vue";

const pincode = "12345";
const questionText = "Do you like cats?";

</script>

<style scoped>
.h3 {
  display:block !important;
  align: right;
}
</style>
