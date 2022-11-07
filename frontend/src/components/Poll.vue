<template>
   <div class="poll">
   <h3> ID: {{ pincode }} </h3>
    <div class="question">
      <h1> Q: {{ questionText }} </h1>
    </div>

    <div class="buttongroup">
      <button class="btn btn-primary btn-lg" type="submit">YES</button>
      <button class="btn btn-primary btn-lg" type="submit">NO</button>
    </div>

   </div>
</template>
  

<script lang="ts">
export default {
  name: 'Poll',
  props: {
    questionText: String
  }
}

const pincode = "12345";

//<button @click="count++">You clicked me --  {{ count }} -- times.</button>
</script>

<style scoped>
.question{
  display: block;
}
.poll{
      text-align: center;
    }
.button{
      text-align: center;
      margin: 20em;
    }
</style>