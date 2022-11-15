<template>
<div class="loginform">
    <br/>
    <h1> Login here </h1>
    <Login />
</div>
</template>

<script lang="ts">
import Login from "@/components/Login.vue";
export default{
    name:"AltLoginView",
    components: {
        Login
    }
}
</script>

