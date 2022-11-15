<template>
    <div class="container">
        <div class="row">
            <div class="col-sm">
                <br/>
                <h1>All users</h1>
                <AllUsers />
            </div>
            <div class="col-sm">
                <br/>
                <h1>All polls</h1>
                <AllPolls />
            </div>
        </div>
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