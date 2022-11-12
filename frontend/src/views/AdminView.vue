<template>
    <div class="allusers">
        <h1>All users</h1>
        <AllUsers />
    </div>

    <div class="allpolls">
        <h1>All polls</h1>
        <AllPolls />
    </div>
</template>

<script lang="ts">
import AllPolls from "@/components/AllPolls.vue";
import AllUsers from "@/components/AllUsers.vue";

export default {
    name: "AdminView",
    components: {
        AllPolls,
        AllUsers
    }
}


</script>

<style scoped>
</style>