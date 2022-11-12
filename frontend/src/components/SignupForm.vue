<template>
    <div class="signupform">
        <label for="username"> Username: </label> 
        <input type="text" id="username" name="username" v-model="username"><br><br>

        <label for="email"> Email: </label>
        <input type="text" id="email" name="email" v-model="email"><br><br>
        
        <label for="password"> Password: </label>
        <input type="password" id="password" name="password" v-model="password"><br><br>

        <p> {{ error }} </p>

        <button v-on:click="signup()" class="btn btn-primary"> Sign up </button> <br/><br/>
        <button v-on:click="redirectToLogin()" class="btn btn-secondary">Log in instead</button>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
 
//import { Buffer } from 'node:buffer';
import axios from 'axios';

export default {
    name: "SingupForm",
    data(){
        return {
            username: '',
            email: '',
            password: '',
            userExists: false,
            createdUser: false,
        } 
    },
    methods: {
        redirectToLogin() {
            this.$router.push({path:"/login"})
        },
        async checkAvailableUsername() {
            await FeedAppDataService.exists(this.username).then((userExists) => this.userExists=userExists);
        },
        async postUser() {
            await FeedAppDataService.postUser(this.username, this.email, this.password).then((status) => {
                if (status == 200) {
                    this.createdUser = true;
                }
            })
        },
        signup() {
            this.checkAvailableUsername().then(() => {
                if(!this.userExists && this.email && this.password) {
                    this.postUser(this.username, this.email, this.password);
                    if (this.createdUser) {
                        alert("User created")
                        this.$router.push({path:"/login"})
                    } else {
                        alert("Something went wrong")
                    }
                } else {
                    this.error = "Username exists and/or fill in all fields"
                }
            })
        } 
    },
    mounted() {
    }
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
