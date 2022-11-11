<template>
    <div class="createpollform">
        <p>please fill out form beneath to create a new poll</p>
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <label for="question"> Question: </label> 
            <input type="text" id="question" name="question" v-model="question"><br><br>

            <label for="startTime"> Start time: </label>
            <input type="datetime-local" id="startTime" name="startTime" v-model="startTime"><br><br>

            <label for="endTime"> End time: </label>
            <input type="datetime-local" id="endTime" name="endTime" v-model="endTime"><br><br>

            <label for="isPrivatepoll"> Private? </label>
            <input type="checkbox" id="isPrivatepoll" name="isPrivatepoll" v-model="isPrivate"> Yes <br><br>

            <button class="btn btn-primary" type="submit" v-on:click="createPoll()"> Create poll </button>

            <p> {{ error }} </p>
        </form>

    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";

export default {
    name: "CreatepollForm",
    data() {
        return {
            question: "",
            startTime: "",
            endTime: "",
            isPrivate: false,
            createdPoll: false,
            username: "",
            password: "",
            error: ""
            }
        },
    methods: {
        retrieveUserCredentialsFromLocalStorage(){
            this.username = localStorage.getItem("username")
            this.password = localStorage.getItem("password")
        },
        async postPoll() {
            this.retrieveUserCredentialsFromLocalStorage();
            await FeedAppDataService.postPoll(this.question, this.startTime, this.endTime, this.isPrivate, this.username, this.password)
            .then((status) => {
                if (status == 200) {
                    this.createdPoll = true;
                }
            });
        },
        checkForm() {
            // check startTime is in future (today -> )

            // check endTime is after startTime

            // check question is filled in
            return true;
        },
        createPoll() {
            const formOK = this.checkForm();
            if (formOK) {
                this.postPoll();
                if (this.createdPoll) {
                    alert("Poll created")
                    this.$router.push({ path: '/voting' })
                } else {
                    alert("Something went wrong")
                }
            } else {
                this.error = "Form is not OK"
            }
        } 
    }
}


</script>

