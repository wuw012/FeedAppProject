<template>
    <div class="loginform">
            <label for="username"> Username: </label> 
            <input type="text" id="username" name="username" v-model="username"><br><br>

            <label for="email"> Email: </label>
            <input type="text" id="email" name="email" v-model="email"><br><br>
            
            <label for="password"> Password: </label>
            <input type="password" id="password" name="password" v-model="password"><br><br>

            
            <button class="btn btn-primary" v-on:click.native="asynclogin()"> Log in </button> <br/><br/>
            <button class="btn btn-secondary" v-on:click="redirectToSignup()"> Sign up instead</button>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';

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
            email: '',
            password: ''
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
        login() {
            //FeedAppDataService.exists(this.username).then((userExists) => console.log("User exists", userExists));
            // does username and password match?
            FeedAppDataService.isAuthenticated(this.username, this.password).then((userAuthenticated) => console.log("User Authenticated", userAuthenticated));
            // is user admin?
            FeedAppDataService.isAdmin("agent_007").then((adminUser) => console.log("Admin user",adminUser));
            
            if(userExists) {
                if(!isAuthenticated) {
                    // If not authenticated, add a path where to redirect after login.
                    this.$router.push({ name: 'login', query: { redirect: '/' } });
                    return "nah, youre just a woman";
                }

                // if admin redirect to Admin-page
                if(adminUser) {
                    this.$router.push({ name: 'login', query: { redirect: '/admin' } });
                    return "u da man";
                    }

                // if normal user redirect to My Polls
                if(userAuthenticated) {
                    this.$router.push({ name: 'login', query: { redirect: '/mypolls' } });
                    return "ure also a man";
                }     
            } 
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
