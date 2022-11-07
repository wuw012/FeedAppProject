<template>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <input type="radio" aria-label="Radio button for following text input">
            </div>
        </div>
        <input type="text" class="form-control" aria-label="Text input with radio button">
</div>
</template>
  

<script lang="ts">
import count from "@/stores/counter.ts"
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