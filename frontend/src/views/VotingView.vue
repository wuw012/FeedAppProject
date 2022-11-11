<template>
  <Poll :questionText="questionText" :pincode="pincode"/>
</template>

<script lang="ts">
import Poll from "@/components/Poll.vue";
export default {
  name: "VotingView",
  components: {
    Poll
  },
  data() {
    return {
      pincode: 0,
      questionText: "",
    }
  },
  methods: {
  }
}
</script>

<style scoped>
.h3 {
  display:block !important;
  align: right;
}
</style>
