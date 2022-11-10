<template>
  <div class="header">
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
import CreatepollFormVeeValidate from "@/components/CreatepollFormVeeValidate.vue";

export default {
  name: 'CreatePollsView',
  components: {
    CreatepollForm,
    CreatepollFormVeeValidate
  }
}
</script>


<style scoped>

</style>