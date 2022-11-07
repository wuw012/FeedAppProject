<template>
  <h3> ID: {{ pincode }} </h3>
  <Poll />
</template>

<script setup lang="ts">
import Poll from "@/components/Poll.vue";

const pincode = "12345";

</script>

<style scoped>
.h3 {
  display:block !important;
  align: right;
}
</style>
