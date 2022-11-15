<template>
    <div class="input-group">
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Input poll id" aria-label="Search" v-model="pollID">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit" @click="searchVote(pollID)">Search</button>
        </form>
    </div>
</template>

<script lang="ts">
export default {
    name: "PollSearch",
    methods: {
        searchVote(pollID) {
            this.$router.push({path: "/voting/"+pollID})
        }
    }
}
</script>

<style>

.input-group {
    margin: 0 auto;
    width: 100%;
    margin-top: 20%;
    margin-left: 40%;
}

</style>