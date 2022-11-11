<template>
    <div class="loginform">
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <label for="username"> Username: </label> 
            <input type="text" id="username" name="username" v-model="username"><br><br>

            <label for="password"> Password: </label>
            <input type="password" id="password" name="password" v-model="password"><br><br>

            <p v-if="error">
                Error!
                {{ error }}
            </p>

            <button class="btn btn-primary" v-on:click="login()" type="submit"> Log in </button> <br/><br/>
            <button class="btn btn-secondary" v-on:click="redirectToSignup()"> Sign up instead</button>
        </form>
        <p>{{error}}</p>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';
import {useStore} from "@/stores/store.js";

export default {
    name: "LogInForm",
    data(){
        return {
            username: '',
            password: '',
            isAuthenticated: false,
            isAdmin: false,
            error: ""
        } 
    },
    methods: {
        redirectToSignup() {
            this.$router.push({ path: '/signup' });
        },
        async updateAuthStatus() {
            await FeedAppDataService.isAuthenticated(this.username, this.password).then((userAuthenticated) => this.isAuthenticated=userAuthenticated);
            await FeedAppDataService.isAdmin(this.username).then((adminUser) => this.isAdmin = adminUser);
        },
        login() {
            this.updateAuthStatus().then(() => {
                if(this.isAuthenticated){
                    localStorage.setItem("username", this.username);
                    localStorage.setItem("password", this.password);
                    if(this.isAdmin) {
                        this.$router.push({ path: '/admin' })
                    }else{
                        this.$router.push({path:"/mypolls"})
                    }
                }
            }).catch(
                console.warn("Could not authenticate user, sign up")
                )
            } 
    },  
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
