<template>
  <PollSearch />
</template>

<script lang="ts">
import PollSearch from "@/components/PollSearch.vue";

export default {
  name: "VotingView",
  components: {
    PollSearch
  }
}
</script>

<style scoped>
.h3 {
  display:block !important;
  align: right;
}
</style>
