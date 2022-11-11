<template>
    <div class="loginform">
        <p>User authenticated: {{isAuthenticated}}</p>
        <form class="form-horizontal">
            <label for="username"> Username: </label> 
            <input type="text" id="username" name="username" v-model="username"><br><br>
            
            <label for="password"> Password: </label>
            <input type="password" id="password" name="password" v-model="password"><br><br>
    
            <button class="btn btn-primary" v-on:click.native="login()"> Log in </button> <br/><br/>
            <button class="btn btn-secondary" v-on:click="redirectToSignup()"> Sign up instead</button>
        </form>
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
            isAdmin: false
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
            // is user admin?
            const store = useStore()
           FeedAppDataService.isAuthenticated(this.username, this.password).then((userAuthenticated) => store.isAuthenticated=userAuthenticated);

            FeedAppDataService.isAdmin(this.username).then((adminUser) => this.isAdmin = adminUser);

 
            store.$patch({
                username:this.username,
                password:this.password,
                })

            } 
    },  
    mounted() {
        const store = useStore();
        alert(store.isAuthenticated)
        if(this.isAdmin) {
                this.$router.push({ path: '/admin' })
            }
        
    }
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
