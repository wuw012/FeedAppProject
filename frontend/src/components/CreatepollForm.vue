<template>
    <div class="createpollform">
        <p>please fill out form beneath to create a new poll</p>

        <form @submit="checkForm" class="form-horizontal" action="/postPoll" method="POST">

            <label for="question"> Question: </label> 
            <input type="text" id="question" name="question" v-model="question"><br><br>
            <p> question {{question}} </p>

            <label for="startTime"> Start time: </label>
            <input type="datetime-local" id="startTime" name="startTime" v-model="startTime"><br><br>
            <p> startTime {{startTime}} </p>

            <label for="endTime"> End time: </label>
            <input type="datetime-local" id="endTime" name="endTime" v-model="endTime"><br><br>
            <p> endTime {{endTime}} </p>

            <label for="isPrivatepoll"> Private? </label>
            <input type="checkbox" id="isPrivatepoll" name="isPrivatepoll" v-model="isPrivatepoll"> Yes <br><br>
            <p> isPrivatepoll {{isPrivatepoll}} </p>

            <input type="submit" value="Submit">

            <p v-if="errors?.length">
                <b>Please correct the following error(s):</b>
                <ul>
                <li v-for="error in errors">{{ error }}</li>
                </ul>
            </p>
        </form>

    </div>
</template>

<script>
const apiUrl = 'https://vuecookbook.netlify.app/.netlify/functions/product-name?name='

export default {
    name: "CreatepollForm",
    data() {
        return {
            form: {
                question: "",
                startTime: "",
                endTime: "",
                private: false,
                errors: [],
            }
        }
    },
    methods:{
        checkForm:function(e) {
            e.preventDefault();
            this.errors = [];
            if(this.question === '') {
                this.errors.push("Question is required.");
            } else {
                fetch(apiUrl+encodeURIComponent(this.question))
                    .then(async res => {
                    if(res.status === 204) {
                        alert('Ok!')
                    } else if(res.status === 400) {
                        let errorResponse = await res.json();
                        this.errors.push(errorResponse.error);
                        }
                    });
                }
            }   
        }
    }

</script>

<style>
input {
    margin-left: 10px;
}
</style>
