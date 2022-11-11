<template>
    <ul v-if="!loading && data && data.length">
    <li v-for="post of data">
        <p><strong></strong></p>
        <p></p>
        </li>
    </ul>

    <p v-if="loading">
        Still loading..
    </p>
    <p v-if="error">
        Error!
    </p>
</template>

<script>
import { ref, computed, onMounted } from "vue";
export default {
    name: 'Posts',
    props: {
    },
    
}


</script>