<template>
<div class="signupform">
    <br/>
    <h1> Sign up here </h1>
    <SignupForm />
</div>
</template>

<script lang="ts">
import SignupForm from "@/components/SignupForm.vue";
export default {
    name:"SignupView",
    components: {
        SignupForm
    }
}
</script>


