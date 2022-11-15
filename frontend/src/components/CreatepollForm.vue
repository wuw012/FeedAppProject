<template>
    <div v-if="this.username" class="wrap">
        <p><strong>Hey {{this.username}}! Fill out the form to see</strong></p>
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <label for="question"> Question: </label> 
            <input type="text" id="question" name="question" v-model="question"><br><br>

            <label for="startTime"> Start time: </label>
            <input type="datetime-local" id="startTime" name="startTime" v-model="startTime"><br><br>

            <label for="endTime"> End time: </label>
            <input type="datetime-local" id="endTime" name="endTime" v-model="endTime"><br><br>

            <label for="isPrivatepoll"> Private? </label>
            <input type="checkbox" id="isPrivatepoll" name="isPrivatepoll" v-model="isPrivate">

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

<style scoped>
.wrap {
  background-color: #ffffff;
  padding: 2%;
  width: 25%;
  min-width:350px;
  margin: 0 auto;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  box-shadow: 0 0 5px #ccc;
  border: 1px solid #fff;
}

input {
  width: 90%;
  margin-bottom: 10px;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #efefef;
  font-size: 15px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input:focus {
  outline: none;
  border-color: #9ecaed;
  box-shadow: 0 0 10px #9ecaed;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input.submit {
  width: 100%;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #45b3e7;
  font-size: 15px;
  background-color: #45b3e7;
  color: #fff;
  margin-top: 25px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input.submit:hover {
  width: 100%;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #32CD32;
  font-size: 15px;
  background-color: #32CD32;
  color: #fff;
  margin-top: 25px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}
</style>

