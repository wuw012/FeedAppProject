<template>
    <div class="loginform">
        <p>User authenticated: {{isAuthenticated}}</p>
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <label for="username"> Username: </label> 
            <input type="text" id="username" name="username" v-model="username"><br><br>
            
            <label for="password"> Password: </label>
            <input type="password" id="password" name="password" v-model="password"><br><br>
    
            
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

/*  
FeedAppDataService.exists("bobleif").then((bobExists) => console.log("Bob exists:",bobExists));

FeedAppDataService.isAuthenticated("bobleif", "totallyhashedandencryptedpassword").then((bobAuthenticated) => console.log("Bob is strapped in and ready to go",bobAuthenticated));

FeedAppDataService.isAdmin("bobleif").then((cookiesTantaDi) => console.log("Sa du?",cookiesTantaDi));

FeedAppDataService.isAdmin("agent_007").then((NOTstirredFFS) => console.log("agent_007 is admin",NOTstirredFFS));
*/
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
        async asynclogin() {
            let response = await axios.get('http://localhost:8080/agents/exists/' + this.username);
            console.log(response.data)
            if (response.data === true){
                FeedAppDataService.isAuthenticated(this.username, this.password)
                .then((bobAuthenticated) => console.log("Bob is strapped in and ready to go",bobAuthenticated));
                alert("making progress???")
                //this.$router.push({ path: '/mypolls' });
                return true;
            }
            
        },
        async updateAuthStatus() {
            await FeedAppDataService.isAuthenticated(this.username, this.password).then((userAuthenticated) => this.isAuthenticated=userAuthenticated);
            await FeedAppDataService.isAdmin(this.username).then((adminUser) => this.isAdmin = adminUser);
        },
        login() {
            this.updateAuthStatus().then(() => {
                if(this.isAuthenticated){
                    if(this.isAdmin) {
                        this.$router.push({ path: '/admin' })
                    }else{
                        this.$router.push({path:"/mypolls"})
                    }

                }
            })
            } 
    },  
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
