<template>
   <div class="poll">

    <div class="question">
      <h1> Question: {{ questionText }} </h1>
    </div>

    <div class="buttongroup">
      <button >YES</button>
      <button>NO</button>
    </div>

   </div>
</template>
  

<script lang="ts">

const questionText = "Do you like cats?"

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
      margin: 2%;
    }
</style>