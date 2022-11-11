<template>
<div class="loginform">
    <h1> Login here </h1>
    <LogInForm />
</div>
</template>

<script lang="ts">
import LogInForm from "@/components/LogInForm.vue";
export default{
    name:"AltLoginView",
    component: {
        LogInForm
    }
}
</script>


<style>

</style>

