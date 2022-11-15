<template>
  <div class="header">
    <br/>
    <h1>Create poll</h1>
  </div>

  <div class="createpollform"> 
    <CreatepollForm />
  </div>

  <div class="createpollform"> 
  </div>
</template>
  

<script lang="ts">
import CreatepollForm from "@/components/CreatepollForm.vue";

export default {
  name: 'CreatePollsView',
  components: {
    CreatepollForm,
  }
}
</script>


<style scoped>

</style>