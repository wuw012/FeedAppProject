<template>
    <div v-if="this.username" class="createpollform">
        <p>Hey {{this.username}}! Please fill out form beneath to create a new poll</p>
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <label for="question"> Question: </label> 
            <input type="text" id="question" name="question" v-model="question"><br><br>

            <label for="startTime"> Start time: </label>
            <input type="datetime-local" id="startTime" name="startTime" v-model="startTime"><br><br>

            <label for="endTime"> End time: </label>
            <input type="datetime-local" id="endTime" name="endTime" v-model="endTime"><br><br>

            <label for="isPrivatepoll"> Private? </label>
            <input type="checkbox" id="isPrivatepoll" name="isPrivatepoll" v-model="isPrivate"> Yes <br><br>

            <p> {{ error }} </p>

            <button class="btn btn-primary" type="submit" v-on:click="createPoll()"> Create poll </button>
        </form>
    </div>

    <div v-if="!this.username">
        <p> You have to log in to create a poll </p>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import moment from "moment";

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
        retrieveUserCredentialsFromLocalStorage() {
            this.username = localStorage.getItem("username")
            this.password = localStorage.getItem("password")
        },
        convertTimeToString(){
            if (this.startTime && this.endTime) {
                this.startTime = moment(String(this.startTime)).format('yyyy-MM-DD HH:mm:ss')
                this.endTime = moment(String(this.endTime)).format('yyyy-MM-DD hh:mm:ss')
            }
        },
        async postPoll() {
            this.convertTimeToString();
            await FeedAppDataService.postPoll(this.question, this.startTime, this.endTime, this.isPrivate, this.username, this.password)
            .then((status) => {
                if (status == 201) {
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
                this.postPoll().then(()=> {
                    if (this.createdPoll) {
                        alert("Poll created")
                        this.$router.push({ path: '/mypolls' })
                    } else {
                        console.log(this.createdPoll)
                        alert("Something went wrong")
                    }
                });
            } else {
                this.error = "Form is not OK"
            }
        } 
    },
    mounted() {
        this.retrieveUserCredentialsFromLocalStorage()
    }
}


</script>

